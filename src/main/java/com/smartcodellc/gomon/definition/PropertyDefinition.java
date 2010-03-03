package com.smartcodellc.gomon.definition;

import com.smartcodellc.gomon.annotations.Mongo;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class PropertyDefinition {

    private Class clazz;
    private String name;
    private String alias;
    private Method writeMethod;
    private Method readMethod;
    private PropertyType propertyType;

    public PropertyDefinition(Field field, PropertyDescriptor propertyDescriptor) {
        clazz = field.getType();
        this.name = propertyDescriptor.getName();
        alias = name;
        if (field.isAnnotationPresent(Mongo.class)) {
            Mongo mongo = field.getAnnotation(Mongo.class);
            if (!"".equals(mongo.value())) {
                alias = mongo.value();
            }
            if (mongo.type() != Void.class) {
                clazz = mongo.type();
            }   
        }
        writeMethod = propertyDescriptor.getWriteMethod();
        readMethod = propertyDescriptor.getReadMethod();
        propertyType = PropertyType.getPropertyType(field.getType());
    }

    public Class getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public Method getWriteMethod() {
        return writeMethod;
    }

    public Method getReadMethod() {
        return readMethod;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }
}
