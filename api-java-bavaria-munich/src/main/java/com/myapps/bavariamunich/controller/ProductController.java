package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.dto.ProductDto;
import com.myapps.bavariamunich.service.ProductService;
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
    public List<ProductDto> readProducts() {
        return productService.readProducts();
    }

    @PostMapping
    public ProductDto createProduct(
            @RequestBody ProductDto productDto
    ) {
        return productService.createProduct(productDto);
    }

}
