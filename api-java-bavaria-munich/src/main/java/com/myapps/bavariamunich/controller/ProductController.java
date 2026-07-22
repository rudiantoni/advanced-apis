package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.controller.base.RequestBodyController;
import com.myapps.bavariamunich.dto.ErrorResponseDto;
import com.myapps.bavariamunich.dto.ProductFullRequestDto;
import com.myapps.bavariamunich.dto.ProductPartialRequestDto;
import com.myapps.bavariamunich.dto.ProductResponseDto;
import com.myapps.bavariamunich.exception.MultiErrorException;
import com.myapps.bavariamunich.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends RequestBodyController {

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
    public ResponseEntity<?> read(
            @PathVariable("id") Long id
    ) {
        try {
            ProductResponseDto result = productService.read(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (MultiErrorException ex) {
            return new ResponseEntity<>(new ErrorResponseDto(ex.getErrors()), ex.getStatus());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody ProductFullRequestDto productFullRequestDto
    ) {
        try {
            ProductResponseDto result = productService.create(productFullRequestDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (MultiErrorException ex) {
            return new ResponseEntity<>(new ErrorResponseDto(ex.getErrors()), ex.getStatus());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replace(
            @PathVariable("id") Long id,
            @RequestBody ProductFullRequestDto productFullRequestDto
    ) {
        try {
            ProductResponseDto result = productService.replace(id, productFullRequestDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (MultiErrorException ex) {
            return new ResponseEntity<>(new ErrorResponseDto(ex.getErrors()), ex.getStatus());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @RequestBody ProductPartialRequestDto productPartialRequestDto
    ) {
        try {
            ProductResponseDto result = productService.update(id, productPartialRequestDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (MultiErrorException ex) {
            return new ResponseEntity<>(new ErrorResponseDto(ex.getErrors()), ex.getStatus());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id
    ) {
        try {
            productService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MultiErrorException ex) {
            return new ResponseEntity<>(new ErrorResponseDto(ex.getErrors()), ex.getStatus());
        }
    }

}
