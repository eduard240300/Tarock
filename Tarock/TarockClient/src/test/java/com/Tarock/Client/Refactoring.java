package com.Tarock.Client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Refactoring<T, K>{
    public Object getVariable(T instance, Field privateField) {
        privateField.setAccessible(true);

        K fieldValue = null;
        try {
            fieldValue = (K) privateField.get(instance);
        } catch (IllegalAccessException ignored) {}
        return fieldValue;
    }
}
