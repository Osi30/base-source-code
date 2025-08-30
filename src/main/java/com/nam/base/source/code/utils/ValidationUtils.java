package com.nam.base.source.code.utils;

public class ValidationUtils {

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
