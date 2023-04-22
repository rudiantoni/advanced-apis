package com.myapps.advancedapijava.modules.product.controller;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.service.ProductService;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private final ProductService service;
  Logger logger = Util.getLogger(this.getClass());

  @GetMapping
  private ResponseEntity<List<ProductDto>> readAll() {
    logger.info("GET /products");
    return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  private ResponseEntity<ProductDto> readOne(
    @PathVariable("id") Long id
  ) {
    logger.info("GET /products/%s".formatted(id));
    try {
      return new ResponseEntity<>(service.readOne(id), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity("Error: %s".formatted(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping
  private ResponseEntity<ProductDto> create(
    @RequestBody ProductDto productDto
  ) {
    logger.info("POST /products");
    return new ResponseEntity<>(service.create(productDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  private ResponseEntity<ProductDto> update(
    @PathVariable("id") Long id,
    @RequestBody ProductDto productDto
  ) {
    logger.info("PUT /products/%s".formatted(id));
    try {
      return new ResponseEntity<>(service.update(id, productDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity("Error: %s".formatted(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/{id}")
  private ResponseEntity<ProductDto> updatePartial(
    @PathVariable("id") Long id,
    @RequestBody ProductDto productDto
  ) {
    logger.info("PATCH /products/%s".formatted(id));
    try {
      return new ResponseEntity<>(service.updatePartial(id, productDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity("Error: %s".formatted(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(
    @PathVariable("id") Long id
  ) {
    try {
      service.deleteById(id);
      return new ResponseEntity<>("Deleted product %s.".formatted(id), HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>("Error: %s".formatted(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

}
