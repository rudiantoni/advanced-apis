package com.myapps.advancedapijava.util;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;

public class Util {
  private Util() {
  }

  static public <T> Logger getLogger(Class<T> c) {
    return (Logger) LoggerFactory.getLogger(c);
  }

  static public <T> String toJsonStr(T obj) {
    String jsonStr = null;
    try {
      jsonStr = new ObjectMapper().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      System.out.printf("Error when converting %s to a JSON String.\n", obj.getClass().getName());
    }
    return jsonStr;
  }

  static public <T> T fromJsonStr(String jsonStr, Class<T> c) {
    T obj = null;
    try {
      obj = new ObjectMapper().readValue(jsonStr, c);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      System.out.printf("Error when converting %s from a JSON String.\n", obj.getClass().getName());
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

}
