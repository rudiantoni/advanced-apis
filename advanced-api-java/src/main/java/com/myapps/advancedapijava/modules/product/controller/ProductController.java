package com.myapps.advancedapijava.modules.product.controller;

import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  @GetMapping("")
  private ResponseEntity<ProductDto> findAll() {
    ProductDto productDto = ProductDto.builder()
      .id(1L)
      .name("Nome teste")
      .description("Descrição teste")
      .price(5F)
      .amount(3).build();

    return new ResponseEntity<>(
      productDto,
      HttpStatus.OK
    );
  }

}
