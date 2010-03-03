package com.smartcodellc.gomon;

/**
 * Based upon http://github.com/julsonlim/mongo-java-driver
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface Operators {

    String GT = "$gt";
    String GTE = "$gte";
    String LT = "$lt";
    String LTE = "$lte";
    String NE = "$ne";
    String IN = "$in";
    String NIN = "$nin";
    String MOD = "$mod";
    String ALL = "$all";
    String SIZE = "$size";
    String EXISTS = "$exists";
    String WHERE = "$where";
}
