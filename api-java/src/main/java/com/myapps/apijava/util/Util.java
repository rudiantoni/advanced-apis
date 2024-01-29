package com.myapps.apijava.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
  private Util() {
  }

  static public <T> String toJsonStr(T obj) {
    String jsonStr = null;
    try {
      jsonStr = new ObjectMapper().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      logError("Error when converting to class from a JSON String", e);
    }
    return jsonStr;
  }

  static public <T> T fromJsonStr(String jsonStr, Class<T> c) {
    T obj = null;
    try {
      obj = new ObjectMapper().readValue(jsonStr, c);
    } catch (JsonProcessingException e) {
      logError("Error when converting from class to a JSON String", e);
    }
    return obj;
  }

  static public void logError(String message, Exception e) {
    System.out.printf("\t[ERROR] UTILITY - %s\n", message);
    System.out.printf("\t%s\n", e.getMessage());
    System.out.println("\n\t[ERROR] UTILITY - Stack trace begin");
    for (StackTraceElement o : e.getStackTrace()) {
      System.out.printf("\t%s\n", o);
    }
    System.out.println("\t[ERROR] UTILITY - Stack trace end\n");
  }
}
