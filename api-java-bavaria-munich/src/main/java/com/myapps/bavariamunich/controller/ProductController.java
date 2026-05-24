package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.dto.ProductDto;
import com.myapps.bavariamunich.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> readProducts() {
        List<ProductDto> result = productService.readProducts();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto
    ) {
        ProductDto result = productService.createProduct(productDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("id") Long id
    ) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
