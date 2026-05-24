package com.myapps.bavariamunich.repository.impl;

import com.myapps.bavariamunich.entity.Product;
import com.myapps.bavariamunich.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryProductRepository.class);
    private final List<Product> products = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public List<Product> findAll() {
        return products.stream()
                .map(Product::copy)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .map(Product::copy);
    }

    @Override
    public Product save(Product product) {
        product.setId(nextId++);
        products.add(product);
        return product.copy();
    }

    @Override
    public void deleteById(Long id) {
        products.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .ifPresent(products::remove);
    }
}
