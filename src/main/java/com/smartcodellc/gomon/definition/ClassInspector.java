package com.smartcodellc.gomon.definition;

import com.smartcodellc.gomon.annotations.Mongo;
import net.sf.cglib.proxy.Enhancer;

import java.beans.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Arrays.asList;

/**
 * This class creates a GomonMapping used to map properties to
 * values stored in the database;
 *
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ClassInspector {

    private static final Set<String> EXCLUDE_LIST = new HashSet<String>(asList("class", "callback", "callbacks"));

    private static final ConcurrentMap<Class, GomonMapping> CACHE = new ConcurrentHashMap<Class, GomonMapping>();

    public static GomonMapping getMongoMapping(Class type) throws IntrospectionException, NoSuchFieldException {

        if (!CACHE.containsKey(type)) {
            Class ctype = type;
            String alias = getClassAlias(ctype);
            GomonMapping definition = new GomonMapping(alias);

            BeanInfo info = Introspector.getBeanInfo(ctype);
            processFields(ctype, info, definition);
            CACHE.putIfAbsent(ctype, definition);
        }
        return CACHE.get(type);
    }

    protected static Field getField(Class type, PropertyDescriptor pd) throws NoSuchFieldException {
        String name = pd.getName();
        if (Enhancer.isEnhanced(type)) {
            type = type.getSuperclass();
        }
        return type.getDeclaredField(name);
    }

    private static String getClassAlias(Class type) {
        if (type.isAnnotationPresent(Mongo.class)) {
            Mongo mongo = (Mongo) type.getAnnotation(Mongo.class);
            return mongo.value();
        } else {
            return type.getSimpleName();
        }
    }

    private static void processFields(Class type, BeanInfo info, GomonMapping definition) throws NoSuchFieldException {
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            if (notExcluding(pd)) {
                Field field = getField(type, pd);
                definition.addPropertyDescriptor(field, pd);
            }
        }
    }

    private static boolean notExcluding(PropertyDescriptor pd) {
        return !EXCLUDE_LIST.contains(pd.getName());
    }

}
