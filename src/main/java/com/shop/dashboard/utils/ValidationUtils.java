package com.shop.dashboard.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public class ValidationUtils {
    private static final String NOT_FOUND ="The %s id: %s was not found in the DB";
    public static void notFound(long id, List<String> errors, String item){
        if(ObjectUtils.isNotEmpty(id)){
            errors.add(String.format(NOT_FOUND, item ,id));
        }
    }
}
