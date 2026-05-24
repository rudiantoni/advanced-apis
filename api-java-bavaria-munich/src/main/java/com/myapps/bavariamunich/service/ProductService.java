package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.dto.ProductDto;
import com.myapps.bavariamunich.entity.Product;
import com.myapps.bavariamunich.mapper.ProductMapper;
import com.myapps.bavariamunich.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> readProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product created = productRepository.save(ProductMapper.toEntity(productDto));
        return ProductMapper.toDto(created);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}

