package com.smartcodellc.gomon;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import static com.smartcodellc.gomon.util.Assert.isNotAnnotation;
import static com.smartcodellc.gomon.util.Assert.isNotEnum;
import static com.smartcodellc.gomon.util.Assert.isNotInterface;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class GomonEnhancer {

    public GomonEnhancer(){
    }

    public <T> T enhance(MethodInterceptor interceptor, Class<T> type) {
        isNotInterface(type, "The provided class must not be an interface.");
        isNotAnnotation(type, "The provided class must not be an annotation.");
        isNotEnum(type, "The provided class must not be an enum.");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setCallback(interceptor);
        return (T) enhancer.create();
    }

}
