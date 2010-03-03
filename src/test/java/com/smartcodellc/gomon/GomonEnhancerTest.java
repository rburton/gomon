package com.smartcodellc.gomon;

import com.smartcodellc.domain.Person;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
@RunWith(MockitoJUnitRunner.class)
public class GomonEnhancerTest {

    private GomonEnhancer mongoEnhancer;

    @Mock
    private MethodInterceptor interceptor;

    @Test
    public void verifyEnhance() throws Throwable {
        when(interceptor.intercept(anyObject(), any(Method.class), any(Object[].class), any(MethodProxy.class)))
                .thenReturn("Richard");

        mongoEnhancer = new GomonEnhancer();
        Person person = mongoEnhancer.enhance(interceptor, Person.class);
        assertEquals("Richard", person.getName());
    }


}
