package com.nam.base.source.code.utils;

import java.util.Collection;

public class ValidationUtils {

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidCollection(Collection<?> collection) {
        if (collection == null) {
            return false;
        }
        return !collection.isEmpty();
    }

    public static boolean isValidNumber(Object value) {
        if (isNullOrEmpty(value.toString())) {
            return false;
        }
        try {
            Double.parseDouble(value.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
