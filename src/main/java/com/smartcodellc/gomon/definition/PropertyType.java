package com.smartcodellc.gomon.definition;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public enum PropertyType {
    PRIMITIVE,
    STRING,
    LIST,
    SET,
    MAP,
    CLASS;

    public static PropertyType getPropertyType(Class clazz) {
        if (clazz.isPrimitive()) {
            return PRIMITIVE;
        } else if (String.class.isAssignableFrom(clazz)) {
            return STRING;
        } else if (List.class.isAssignableFrom(clazz)) {
            return LIST;
        } else if (Set.class.isAssignableFrom(clazz)) {
            return SET;
        } else if (Map.class.isAssignableFrom(clazz)) {
            return MAP;
        } else {
            return CLASS;
        }
    }
}
