package com.myapps.advancedapijava.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
  private DateUtil() {
  }

  public static Date plusHours(Date date, Integer hours) {
    return new Date(date.getTime() + TimeUnit.HOURS.toMillis(hours));
  }

}
