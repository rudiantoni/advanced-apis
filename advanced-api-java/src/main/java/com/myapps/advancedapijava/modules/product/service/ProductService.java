package com.myapps.advancedapijava.modules.product.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.util.Util;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  Logger logger = Util.getLogger(this.getClass());

  public void findAll() {
    logger.info("Called service.");
  }
}
