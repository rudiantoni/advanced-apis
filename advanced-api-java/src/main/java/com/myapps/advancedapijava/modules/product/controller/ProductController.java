package com.myapps.advancedapijava.modules.product.controller;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.service.ProductService;
import com.myapps.advancedapijava.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
  private ProductService productService;

  public ProductController(
    ProductService productService
  ) {
    this.productService = productService;
  }

  Logger logger = Util.getLogger(this.getClass());

  @GetMapping("")
  private ResponseEntity<ProductDto> findAll() {
    logger.info("Called controller");

    ProductDto productDto = ProductDto.builder()
      .id(1L)
      .name("Nome teste")
      .description("Descrição teste")
      .price(5F)
      .amount(3).build();

    productService.findAll();
    return new ResponseEntity<>(
      productDto,
      HttpStatus.OK
    );
  }

}
