package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.dto.ProductDto;
import com.myapps.bavariamunich.dto.ProductUpdateDto;
import com.myapps.bavariamunich.dto.ProductWriteDto;
import com.myapps.bavariamunich.entity.Product;
import com.myapps.bavariamunich.mapper.ProductMapper;
import com.myapps.bavariamunich.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> readAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto read(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDto)
                .orElseThrow(() -> {
                    logger.warn("Product not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
                });
    }

    public ProductDto create(ProductWriteDto given) {
        Product created = productRepository.save(ProductMapper.toEntity(given));
        return ProductMapper.toDto(created);
    }

    public ProductDto replace(Long id, ProductWriteDto given) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
                });
        ProductMapper.replaceEntity(existing, given);
        Product saved = productRepository.save(existing);
        return ProductMapper.toDto(saved);
    }

    public ProductDto update(Long id, ProductUpdateDto given) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
                });
        ProductMapper.updateEntity(existing, given);
        Product saved = productRepository.save(existing);
        return ProductMapper.toDto(saved);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}

