package com.myapps.advancedapijava.util;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Util {
  private Util() {
  }

  public static List<String> ALLOWED_URLS = Arrays.asList(
    "/auth/**",
    "/tests/**",
    "/swagger-ui/**",
    "/v3/api-docs/**"
  );

  static public <T> Logger getLogger(Class<T> c) {
    return (Logger) LoggerFactory.getLogger(c);
  }

  static public <T> String toJsonStr(T obj) {
    String jsonStr = null;
    try {
      jsonStr = new ObjectMapper().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      System.out.printf("Error when converting from class %s to a JSON String.\n", obj.getClass().getName());
    }
    return jsonStr;
  }

  static public <T> T fromJsonStr(String jsonStr, Class<T> c) {
    T obj = null;
    String className = "(unable to read classname)";
    try {
      obj = new ObjectMapper().readValue(jsonStr, c);
      className = obj.getClass().getName();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      System.out.printf("Error when converting to class %s from a JSON String.\n", className);
    }
    return obj;
  }

  static public <T> T getOrDefault(T value, T defaultValue) {
    if (value != null) {
      return value;
    } else {
      return defaultValue;
    }
  }

  static public <T> T[] toArray(List<T> list, Class<T[]> c) {
    T[] array = c.cast(Array.newInstance(c.getComponentType(), list.size()));
    return list.toArray(array);
  }

}
