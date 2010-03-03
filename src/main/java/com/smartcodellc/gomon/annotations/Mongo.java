package com.smartcodellc.gomon.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Mongo {

    String value() default "";
    Class type() default Void.class;
}
