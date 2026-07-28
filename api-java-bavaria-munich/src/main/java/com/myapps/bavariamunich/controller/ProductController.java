package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.dto.ProductFullRequestDto;
import com.myapps.bavariamunich.dto.ProductPartialRequestDto;
import com.myapps.bavariamunich.dto.ProductResponseDto;
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
    public ResponseEntity<List<ProductResponseDto>> readAll() {
        List<ProductResponseDto> result = productService.readAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> read(
            @PathVariable("id") Long id
    ) {
        ProductResponseDto result = productService.read(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(
            @RequestBody ProductFullRequestDto productFullRequestDto
    ) {
        ProductResponseDto result = productService.create(productFullRequestDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> replace(
            @PathVariable("id") Long id,
            @RequestBody ProductFullRequestDto productFullRequestDto
    ) {
        ProductResponseDto result = productService.replace(id, productFullRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody ProductPartialRequestDto productPartialRequestDto
    ) {
        ProductResponseDto result = productService.update(id, productPartialRequestDto);
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
