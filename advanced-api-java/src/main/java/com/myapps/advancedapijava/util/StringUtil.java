package com.myapps.advancedapijava.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
  private StringUtil() {
  }

  public static Boolean strIsValidEmail(String text) {
    Pattern pattern = Pattern.compile("^[\\w-_.+]*[\\w-_.]@[\\w]+[\\w-_.]+[\\w]$");
    Matcher matcher = pattern.matcher(text);
    return matcher.matches();
  }

  public static Boolean strHasValue(String text) {
    return (text != null && !text.isBlank());
  }

  public static Boolean strHasNoValue(String text) {
    return text == null || text.isBlank();
  }

}
