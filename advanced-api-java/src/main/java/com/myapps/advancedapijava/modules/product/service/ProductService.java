package com.myapps.advancedapijava.modules.product.service;

import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.entity.Product;
import com.myapps.advancedapijava.modules.product.repository.ProductRepository;
import com.myapps.advancedapijava.modules.product.util.ProductUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.myapps.advancedapijava.util.StringUtil.strHasNoValue;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository repository;

  public Product findByIdOrException(Long id) {
    return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Product with id %s not found.".formatted(id)));
  }

  public void existsByIdOrException(Long id) {
    if (!repository.existsById(id)) {
      throw new NoSuchElementException("Product with id %s not found.".formatted(id));
    }
  }

  public List<ProductDto> readAll() {
    List<Product> productList = repository.findAll();
    return ProductUtil.toDtoList(productList);
  }

  public ProductDto create(ProductDto productDto) {
    validateRequiredFields(productDto);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productSaved = repository.save(productReceived);
    return ProductUtil.toDto(productSaved);
  }

  public ProductDto readOne(Long id) {
    return ProductUtil.toDto(findByIdOrException(id));
  }

  public ProductDto update(Long id, ProductDto productDto) {
    existsByIdOrException(id);
    Product productStored = findByIdOrException(id);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productUpdated = ProductUtil.updateEntityNoId(productStored, productReceived);
    validateRequiredFields(productDto);
    Product productSaved = repository.save(productUpdated);
    return ProductUtil.toDto(productSaved);
  }

  public ProductDto updatePartial(Long id, ProductDto productDto) {
    Product productStored = findByIdOrException(id);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productUpdated = ProductUtil.updateEntityNoIdNotNull(productStored, productReceived);
    validateRequiredFields(ProductUtil.toDto(productUpdated));
    Product productSaved = repository.save(productUpdated);
    return ProductUtil.toDto(productSaved);
  }

  public void deleteById(Long id) {
    existsByIdOrException(id);
    repository.deleteById(id);
  }

  public void validateRequiredFields(ProductDto productDto) {
    if (strHasNoValue(productDto.getName())) {
      throw new IllegalArgumentException("Product name is required.");
    } else if (strHasNoValue(productDto.getDescription())) {
      throw new IllegalArgumentException("Product description is required.");
    }

  }


}
