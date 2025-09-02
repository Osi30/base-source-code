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
}
