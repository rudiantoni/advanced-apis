package com.myapps.bavariamunich.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.jackson.nullable.JsonNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper;

    public static final TypeReference<List<String>> LIST_STRING = new TypeReference<List<String>>() {};

    private JsonUtil() {
    }

    public static void setObjectMapper(ObjectMapper mapper) {
        if (objectMapper == null) {
            objectMapper = mapper;
        }
    }

    private static void ensureMapperConfigured() {
        if (objectMapper == null) {
            throw new IllegalStateException("JsonUtil not initialized - ObjectMapper was not set");
        }
    }

    public static <T> void applyIfDefined(JsonNullable<T> value, Consumer<T> setter) {
        if (value == null || value.isUndefined()) {
            return;
        }
        setter.accept(value.get());
    }
    
    public static <T> T fromJsonStr(String jsonStr, Class<T> c) {
        ensureMapperConfigured();
        T obj = null;
        try {
            obj = objectMapper.readValue(jsonStr, c);
        } catch (JsonProcessingException e) {
            logger.error("Error when converting to class {} from a JSON String.", c.getName(), e);
        }
        return obj;
    }

    public static <T> T fromJsonStr(String jsonStr, TypeReference<T> typeRef) {
        ensureMapperConfigured();
        try {
            return objectMapper.readValue(jsonStr, typeRef);
        } catch (JsonProcessingException e) {
            logger.error("Error when converting from JSON String to type {}", typeRef.getType(), e);
            return null;
        }
    }

    public static <T> String toJsonStr(T obj) {
        ensureMapperConfigured();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Error when converting from class {} to a JSON String.", obj.getClass().getName(), e);
            return null;
        }
    }

}
