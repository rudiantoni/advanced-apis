package com.myapps.advancedapijava.modules.product.controller;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.entity.Product;
import com.myapps.advancedapijava.modules.product.service.ProductService;
import com.myapps.advancedapijava.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
  private final ProductService service;

  public ProductController(
    ProductService service
  ) {
    this.service = service;
  }

  Logger logger = Util.getLogger(this.getClass());

  @GetMapping("")
  private ResponseEntity<List<Product>> findAll() {
    logger.info("GET /products");

    return new ResponseEntity<>(
      service.findAll(),
      HttpStatus.OK
    );

  }

  @PostMapping("")
  private ResponseEntity<Product> save(
    @RequestBody Product product
  ) {
    logger.info("POST /products");

    return new ResponseEntity<>(
      service.save(product),
      HttpStatus.OK
    );

  }

  @GetMapping("/{id}")
  private ResponseEntity<Product> findById(
    @PathVariable("id") Long id
  ) {
    logger.info("GET /products/%s".formatted(id));

    return new ResponseEntity<>(
      service.findById(id),
      HttpStatus.OK
    );

  }
}
