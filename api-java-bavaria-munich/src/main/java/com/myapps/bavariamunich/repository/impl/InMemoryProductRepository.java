package com.myapps.bavariamunich.repository.impl;

import com.myapps.bavariamunich.entity.Product;
import com.myapps.bavariamunich.repository.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryProductRepository implements ProductRepository {

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
        boolean isUpdate = product.getId() != null;
        if (isUpdate) {
            Optional<Product> found = products.stream()
                    .filter(it -> Objects.equals(it.getId(), product.getId()))
                    .findFirst();
            if (found.isPresent()) {
                int index = products.indexOf(found.get());
                Product persisted = product.copy();
                products.set(index, persisted);
                return product.copy();
            } else {
                throw new EmptyResultDataAccessException("Entity with id " + product.getId() + " not found", 1);
            }
        } else { // isSave
            product.setId(nextId++);
            products.add(product);
            return product.copy();
        }
    }

    @Override
    public void deleteById(Long id) {
        products.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .ifPresent(products::remove)
        ;
    }
}
