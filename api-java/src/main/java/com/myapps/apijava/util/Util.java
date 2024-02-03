package com.myapps.apijava.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Util {
  private Util() {
  }

  static public <T> String toJsonStr(T obj) {
    String jsonStr = null;
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      jsonStr = mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      logError("UTILITY", "Error when converting to class from a JSON String", e);
    }
    return jsonStr;
  }

  static public <T> T fromJsonStr(String jsonStr, Class<T> c) {
    T obj = null;
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      obj = mapper.readValue(jsonStr, c);
    } catch (JsonProcessingException e) {
      logError("UTILITY", "Error when converting from class to a JSON String", e);
    }
    return obj;
  }

  static public void logError(String tag, String message, Exception e) {
    System.out.printf("\t[ERROR] %s - %s\n", tag, message);
    System.out.printf("\t%s\n", e.getMessage());
    System.out.printf("\n\t[ERROR] %s - Stack trace begin\n", tag);
    for (StackTraceElement o : e.getStackTrace()) {
      System.out.printf("\t%s\n", o);
    }
    System.out.printf("\t[ERROR] %s - Stack trace end\n", tag);
  }
}
