package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.dto.ProductFullRequestDto;
import com.myapps.bavariamunich.dto.ProductPartialRequestDto;
import com.myapps.bavariamunich.dto.ProductResponseDto;
import com.myapps.bavariamunich.entity.Product;
import com.myapps.bavariamunich.exception.MultiErrorException;
import com.myapps.bavariamunich.mapper.ProductMapper;
import com.myapps.bavariamunich.repository.ProductRepository;
import com.myapps.bavariamunich.service.validation.ProductValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductValidationService productValidationService;

    public ProductService(
            ProductRepository productRepository,
            ProductValidationService productValidationService
    ) {
        this.productRepository = productRepository;
        this.productValidationService = productValidationService;
    }

    public List<ProductResponseDto> readAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto read(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDto)
                .orElseThrow(() -> {
                    logger.warn("Product not found with id: {}", id);
                    return new MultiErrorException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
                });
    }

    public ProductResponseDto create(ProductFullRequestDto given) {
        productValidationService.validateFull(given, "Unable to create product");
        Product created = productRepository.save(ProductMapper.toEntity(given));
        return ProductMapper.toDto(created);
    }

    public ProductResponseDto replace(Long id, ProductFullRequestDto given) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product not found with id: {}", id);
                    return new MultiErrorException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
                });
        productValidationService.validateFull(given, "Unable to replace product with id: " + id);
        ProductMapper.replaceEntity(existing, given);
        Product saved = productRepository.save(existing);
        return ProductMapper.toDto(saved);
    }

    public ProductResponseDto update(Long id, ProductPartialRequestDto given) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product not found with id: {}", id);
                    return new MultiErrorException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
                });
        productValidationService.validatePartial(given, "Unable to update product with id: " + id);
        ProductMapper.updateEntity(existing, given);
        Product saved = productRepository.save(existing);
        return ProductMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            logger.warn("Product not found with id: {}", id);
            throw new MultiErrorException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
