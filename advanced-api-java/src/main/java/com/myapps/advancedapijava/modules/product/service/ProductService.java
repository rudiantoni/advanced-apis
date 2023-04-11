package com.myapps.advancedapijava.modules.product.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.entity.Product;
import com.myapps.advancedapijava.modules.product.repository.ProductRepository;
import com.myapps.advancedapijava.modules.product.util.ProductUtil;
import com.myapps.advancedapijava.util.Util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
  private final ProductRepository repository;

  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  Logger logger = Util.getLogger(this.getClass());

  public List<ProductDto> findAll() {
    List<Product> productList = repository.findAll();
    return ProductUtil.toDtoList(productList);
  }

  public ProductDto save(ProductDto productDto) {
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productSaved = repository.save(productReceived);
    return ProductUtil.toDto(productSaved);
  }

  public Product findByIdOrNotFound(Long id) throws Exception {
    return repository.findById(id).orElseThrow(() -> new Exception("Product %s not found.".formatted(id)));
  }

  public ProductDto findById(Long id) throws Exception {
    return ProductUtil.toDto(findByIdOrNotFound(id));
  }

  public Boolean existsById(Long id) {
    return repository.existsById(id);
  }

  public void existsByIdOrNotFound(Long id) throws Exception {
    if (!existsById(id))
      throw new Exception("Product %s not found.".formatted(id));
  }

  public ProductDto update(Long id, ProductDto productDto) throws Exception {
    existsByIdOrNotFound(id);
    Product productStored = findByIdOrNotFound(id);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productUpdated = ProductUtil.updateEntityNoId(productStored, productReceived);
    Product productSaved = repository.save(productUpdated);
    return ProductUtil.toDto(productSaved);
  }

  public ProductDto updatePartial(Long id, ProductDto productDto) throws Exception {
    Product productStored = findByIdOrNotFound(id);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productToSave = ProductUtil.updateEntityNoIdNotNull(productStored, productReceived);
    Product productSave = repository.save(productToSave);
    return ProductUtil.toDto(productSave);
  }

  public void deleteById(Long id) throws Exception {
    existsByIdOrNotFound(id);
    repository.deleteById(id);
  }

}
