package com.smartcodellc.definition;

import com.smartcodellc.domain.Person;
import com.smartcodellc.gomon.definition.ClassInspector;
import com.smartcodellc.gomon.definition.GomonMapping;

import static com.smartcodellc.gomon.definition.PropertyType.*;
import org.junit.Test;

import java.beans.IntrospectionException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ClassInspectorTest {

    private ClassInspector classInspector = new ClassInspector();

    @Test
    public void verifyInspectPojo() throws IntrospectionException, NoSuchFieldException {
        GomonMapping mapping = ClassInspector.getMongoMapping(Person.class);
        assertEquals(3, mapping.propertyCount());

        assertNotNull(mapping.getProperty("age"));
        assertEquals(PRIMITIVE, mapping.getProperty("age").getPropertyType());

        assertNotNull(mapping.getProperty("name"));
        assertEquals(STRING, mapping.getProperty("name").getPropertyType());
        assertEquals("n", mapping.getProperty("name").getAlias());

        assertNotNull(mapping.getProperty("child"));
        assertEquals(CLASS, mapping.getProperty("child").getPropertyType());
    }

}
