package com.myapps.bavariamunich.repository.impl;

import com.myapps.bavariamunich.entity.Product;
import com.myapps.bavariamunich.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryProductRepository.class);
    private final List<Product> products = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public List<Product> findAll() {
        return this.products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product save(Product product) {
        product.setId(nextId++);
        products.add(product);
        return product;
    }

    @Override
    public void deleteById(Long id) {
        Product product = products.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("Product not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
                });
        products.remove(product);
    }
}
