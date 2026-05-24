package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final List<ProductDto> products = new ArrayList<>();
    private Long nextId = 1L;

    private List<ProductDto> repoFindProducts() {
        return this.products;
    }

    private ProductDto repoSaveProduct(ProductDto productDto) {
        ProductDto newProduct = new ProductDto(nextId++, productDto.getDescription());
        products.add(newProduct);
        return newProduct;
    }

    private void repoRemoveProduct(Long id) {
        ProductDto product = products.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("Product not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
                });
        products.remove(product);
    }


    public List<ProductDto> readProducts() {
        return repoFindProducts();
    }

    public ProductDto createProduct(ProductDto productDto) {
        return repoSaveProduct(productDto);
    }

    public void deleteProduct(Long id) {
        repoRemoveProduct(id);
    }

}

