package com.myapps.advancedapijava.modules.product.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.product.entity.Product;
import com.myapps.advancedapijava.modules.product.repository.ProductRepository;
import com.myapps.advancedapijava.util.Util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
  private final ProductRepository repository;

  public ProductService(
    ProductRepository repository
  ) {
    this.repository = repository;
  }

  Logger logger = Util.getLogger(this.getClass());

  public List<Product> findAll() {
    return repository.findAll();
  }

  public Product save(Product product) {
    return repository.save(product);
  }


}
