package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.dto.ProductDto;
import com.myapps.bavariamunich.dto.ProductUpdateDto;
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
    public ResponseEntity<List<ProductDto>> readAll() {
        List<ProductDto> result = productService.readAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> read(
            @PathVariable("id") Long id
    ) {
        ProductDto result = productService.read(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(
            @RequestBody ProductDto productDto
    ) {
        ProductDto result = productService.create(productDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> replace(
            @PathVariable("id") Long id,
            @RequestBody ProductDto productDto
    ) {
        ProductDto result = productService.replace(id, productDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> update(
            @PathVariable("id") Long id,
            @RequestBody ProductUpdateDto productUpdateDto
    ) {
        ProductDto result = productService.update(id, productUpdateDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id
    ) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
