package com.myapps.bavariamunich.util;

import org.openapitools.jackson.nullable.JsonNullable;

import java.util.function.Consumer;

public class JsonUtil {

    private JsonUtil() {
    }

    public static <T> void applyIfDefined(JsonNullable<T> value, Consumer<T> setter) {
        if (value == null || value.isUndefined()) {
            return;
        }
        setter.accept(value.get());
    }

    public static <T> boolean isDefined(JsonNullable<T> value) {
        return value != null && !value.isUndefined();
    }

}
