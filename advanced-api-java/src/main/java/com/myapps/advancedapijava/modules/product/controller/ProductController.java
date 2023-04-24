package com.myapps.advancedapijava.modules.product.controller;

import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.service.ProductService;
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

  @GetMapping
  public ResponseEntity<List<ProductDto>> readAll() {
    return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> readOne(
    @PathVariable("id") Long id
  ) {
      return new ResponseEntity<>(service.readOne(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ProductDto> create(
    @RequestBody ProductDto productDto
  ) {
    return new ResponseEntity<>(service.create(productDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> update(
    @PathVariable("id") Long id,
    @RequestBody ProductDto productDto
  ) {
    try {
      return new ResponseEntity<>(service.update(id, productDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity("Error: %s".formatted(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ProductDto> updatePartial(
    @PathVariable("id") Long id,
    @RequestBody ProductDto productDto
  ) {
    try {
      return new ResponseEntity<>(service.updatePartial(id, productDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity("Error: %s".formatted(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(
    @PathVariable("id") Long id
  ) {
    try {
      service.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>("Error: %s".formatted(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

}
