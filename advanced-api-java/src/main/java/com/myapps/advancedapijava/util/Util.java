package com.myapps.advancedapijava.util;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class Util {
  private Util() {
  }

  static public <T> Logger getLogger(Class<T> c) {
    return (Logger) LoggerFactory.getLogger(c);
  }

}
