package com.smartcodellc.gomon.proxy;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.smartcodellc.gomon.DBObjectLoader;
import com.smartcodellc.gomon.GomonEnhancer;
import com.smartcodellc.gomon.bean.PropertyNormalizer;
import com.smartcodellc.gomon.definition.ClassInspector;
import com.smartcodellc.gomon.definition.GomonMapping;
import com.smartcodellc.gomon.definition.PropertyDefinition;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

import static com.smartcodellc.gomon.util.Assert.notNull;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class DBObjectProxy implements MethodInterceptor {

    private PropertyNormalizer propertyNormalizer = new PropertyNormalizer();

    protected DBObject dbObject;

    protected DBObjectLoader dbObjectLoader;

    protected GomonEnhancer enhancer;

    public DBObjectProxy(GomonEnhancer enhancer, DBObjectLoader dbObjectLoader) {
        this.enhancer = enhancer;
        this.dbObjectLoader = dbObjectLoader;
    }

    public Object intercept(Object instance, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
        notNull(dbObject, "The DBObject is required, but has not been set.");
        String property = propertyNormalizer.normalize(method.getName());

        GomonMapping mapping = ClassInspector.getMongoMapping(instance.getClass());
        PropertyDefinition propertyDefinition = mapping.getProperty(property);
        property = propertyDefinition.getAlias();
        
        if (property != null) {
            Object value = dbObject.get(property);

            if (value instanceof BasicDBList) {
                BasicDBList dbList = (BasicDBList) value;
                return new LazyDBObjectList(enhancer, dbList, dbObjectLoader, propertyDefinition.getClazz());
            }else if (value instanceof DBObject) {
                return getProxyProperty(value, method);
            } else if (value instanceof DBRef) {
                DBRef ref = (DBRef) value;
                value = dbObjectLoader.load((String) ref.getId());
                return getProxyProperty(value, method);
            } else {
                return value;
            }
        } else {
            return methodProxy.invokeSuper(instance, arguments);
        }
    }

    protected Object getProxyProperty(Object value, Method method) {
        DBObject dbObject = (DBObject) value;
        Class<?> returnType = method.getReturnType();
        DBObjectProxy proxy = new DBObjectProxy(enhancer, dbObjectLoader);
        proxy.setDbObject(dbObject);
        return enhancer.enhance(proxy, returnType);
    }

    public void setDbObject(DBObject dbObject) {
        this.dbObject = dbObject;
    }

}
