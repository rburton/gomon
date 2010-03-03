package com.smartcodellc.gomon.definition;

import com.smartcodellc.gomon.bean.PropertyNormalizer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class GomonMapping {

    private Map<String, PropertyDefinition> properties = new HashMap<String, PropertyDefinition>();

    private PropertyNormalizer propertyNormalizer = new PropertyNormalizer();

    private String alias;

    public GomonMapping(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public PropertyDefinition getProperty(String property) {
        return properties.get(property);
    }

    public void addPropertyDescriptor(Field field, PropertyDescriptor pd) {
        String property = field.getName();
        properties.put(property, new PropertyDefinition(field, pd));
    }

    public int propertyCount() {
        return properties.size();
    }
}
