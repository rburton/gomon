package com.smartcodellc.gomon.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class PropertyNormalizer {

    private static final Pattern GET_PROPERTY = Pattern.compile("get(.*)");
    private static final Pattern SET_PROPERTY = Pattern.compile("set(.*)");

    public String normalize(String name) {
        if (isGetter(name)) {
            return getProperty(GET_PROPERTY, name);
        } else {
            return getProperty(SET_PROPERTY, name);
        }
    }

    private boolean isGetter(String name) {
        return name.startsWith("get");
    }


    protected String getProperty(Pattern pattern, String name) {
        Matcher matcher = pattern.matcher(name);
        String property = null;
        if (matcher.find()) {
            property = matcher.group(1);
            property = lowerCase(property);
        }
        return property;
    }

    protected String lowerCase(String id) {
        char firstChar = Character.toLowerCase(id.charAt(0));
        if (id.length() > 1) {
            return firstChar + id.substring(1);
        } else {
            return String.valueOf(firstChar);
        }
    }

}
