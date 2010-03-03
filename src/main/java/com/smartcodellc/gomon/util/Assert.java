package com.smartcodellc.gomon.util;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class Assert {

    public static void notNull(Object value, String error){
        if(value == null){
            throw new IllegalArgumentException(error);
        }
    }

    public static void isNotInterface(Class clazz, String error){
        if(clazz.isInterface()){
            throw new IllegalArgumentException(error);
        }
    }

    public static void isNotAnnotation(Class clazz, String error){
        if(clazz.isAnnotation()){
            throw new IllegalArgumentException(error);
        }
    }

    public static void isNotEnum(Class clazz, String error){
        if(clazz.isEnum()){
            throw new IllegalArgumentException(error);
        }
    }

}
