package com.myapps.advancedapijava.modules.product.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.entity.Product;
import com.myapps.advancedapijava.modules.product.repository.ProductRepository;
import com.myapps.advancedapijava.modules.product.util.ProductUtil;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository repository;
  Logger logger = Util.getLogger(this.getClass());

  public Product findByIdOrException(Long id) {
    return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Product with id %s not found.".formatted(id)));
  }

  public void existsByIdOrException(Long id) {
    if (!repository.existsById(id)) {
      throw new NoSuchElementException("Product with id %s not found.".formatted(id));
    }
  }

  public ProductDto create(ProductDto productDto) {
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productSaved = repository.save(productReceived);
    return ProductUtil.toDto(productSaved);
  }

  public ProductDto readOne(Long id) {
    return ProductUtil.toDto(findByIdOrException(id));
  }

  public List<ProductDto> readAll() {
    List<Product> productList = repository.findAll();
    return ProductUtil.toDtoList(productList);
  }

  public ProductDto update(Long id, ProductDto productDto) {
    existsByIdOrException(id);
    Product productStored = findByIdOrException(id);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productUpdated = ProductUtil.updateEntityNoId(productStored, productReceived);
    Product productSaved = repository.save(productUpdated);
    return ProductUtil.toDto(productSaved);
  }

  public ProductDto updatePartial(Long id, ProductDto productDto) {
    Product productStored = findByIdOrException(id);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productToSave = ProductUtil.updateEntityNoIdNotNull(productStored, productReceived);
    Product productSave = repository.save(productToSave);
    return ProductUtil.toDto(productSave);
  }

  public void deleteById(Long id) {
    existsByIdOrException(id);
    repository.deleteById(id);
  }

}
