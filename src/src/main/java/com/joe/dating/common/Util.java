package com.joe.dating.common;

public class Util {
    public static boolean getBooleanFromDb(String val) {
        return "Y".equalsIgnoreCase(val);
    }

    public static String getBooleanForDb(boolean val) {
        return val ? "Y" : "N";
    }
}
