package com.smartcodellc.gomon.proxy;

import com.smartcodellc.gomon.DBObjectLoader;
import com.smartcodellc.gomon.GomonEnhancer;
import com.smartcodellc.gomon.proxy.DBObjectProxy;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class LazyDBObjectProxy extends DBObjectProxy {

    private String id;

    public LazyDBObjectProxy(GomonEnhancer enhancer, String id, DBObjectLoader dbObjectLoader) {
        super(enhancer, dbObjectLoader);
        this.id = id;
    }

    public Object intercept(Object instance, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
        if (dbObject == null) {
            dbObject = dbObjectLoader.load(id);
            if (dbObject == null) {
                throw new IllegalArgumentException("No object found for the provided id " + id);
            }
        }
        return super.intercept(instance, method, arguments, methodProxy);
    }

}
