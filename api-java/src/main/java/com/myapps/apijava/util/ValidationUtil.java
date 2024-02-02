package com.myapps.apijava.util;

import java.util.Optional;

public class ValidationUtil {
  private ValidationUtil() {
  }

  private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

  static public Boolean isNullOrBlank(String str) {
    return Optional.ofNullable(str).map(String::isBlank).orElse(true);
  }

  public static Boolean isValidEmail(String email) {
    return Optional.ofNullable(email).map(s -> s.matches(EMAIL_REGEX)).orElse(false);
  }

}
