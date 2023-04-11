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

  public ProductService(
    ProductRepository repository
  ) {
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

  public Product findByIdOrNotFound(Long id) {
    try {
      return repository.findById(id).orElseThrow(() -> new Exception("Product %s not found.".formatted(id)));
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Product %s not found.".formatted(id));
    }
    return null;
  }

  public ProductDto findById(Long id) {
    return ProductUtil.toDto(findByIdOrNotFound(id));
  }
}
