package com.myapps.advancedapijava.modules.product.controller;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.product.dto.ProductDto;
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
  private ResponseEntity<List<ProductDto>> findAll() {
    logger.info("GET /products");
    return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  private ResponseEntity<ProductDto> findById(
    @PathVariable("id") Long id
  ) {
    logger.info("GET /products/%s".formatted(id));
    try {
      return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity("Error: %s".formatted(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("")
  private ResponseEntity<ProductDto> save(
    @RequestBody ProductDto productDto
  ) {
    logger.info("POST /products");
    return new ResponseEntity<>(service.save(productDto), HttpStatus.CREATED);
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
