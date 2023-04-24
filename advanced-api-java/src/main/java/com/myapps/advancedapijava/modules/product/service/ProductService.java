package com.myapps.advancedapijava.modules.product.service;

import com.myapps.advancedapijava.enums.ExceptionType;
import com.myapps.advancedapijava.exception.HandledException;
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

  public Product findByIdOrException(Long id) throws HandledException {
    return repository.findById(id).orElseThrow(() -> new HandledException("Product with id %s not found.".formatted(id), ExceptionType.PRODUCT_NOT_FOUND_BY_ID));
  }

  public void existsByIdOrException(Long id) throws HandledException {
    if (!repository.existsById(id)) {
      throw new HandledException("Product with id %s not found.".formatted(id), ExceptionType.PRODUCT_NOT_FOUND_BY_ID);
    }
  }

  public List<ProductDto> readAll() {
    List<Product> productList = repository.findAll();
    return ProductUtil.toDtoList(productList);
  }

  public ProductDto create(ProductDto productDto) throws HandledException {
    validateRequiredFields(productDto);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productSaved = repository.save(productReceived);
    return ProductUtil.toDto(productSaved);
  }

  public ProductDto readOne(Long id) throws HandledException {
    return ProductUtil.toDto(findByIdOrException(id));
  }

  public ProductDto update(Long id, ProductDto productDto) throws HandledException {
    existsByIdOrException(id);
    Product productStored = findByIdOrException(id);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productUpdated = ProductUtil.updateEntityNoId(productStored, productReceived);
    validateRequiredFields(productDto);
    Product productSaved = repository.save(productUpdated);
    return ProductUtil.toDto(productSaved);
  }

  public ProductDto updatePartial(Long id, ProductDto productDto) throws HandledException {
    Product productStored = findByIdOrException(id);
    Product productReceived = ProductUtil.toEntityNoId(productDto);
    Product productUpdated = ProductUtil.updateEntityNoIdNotNull(productStored, productReceived);
    validateRequiredFields(ProductUtil.toDto(productUpdated));
    Product productSaved = repository.save(productUpdated);
    return ProductUtil.toDto(productSaved);
  }

  public void deleteById(Long id) throws HandledException {
    existsByIdOrException(id);
    repository.deleteById(id);
  }

  public void validateRequiredFields(ProductDto productDto) throws HandledException {
    if (strHasNoValue(productDto.getName())) {
      throw new HandledException(ExceptionType.PRODUCT_REQUIRED_NAME);

    } else if (strHasNoValue(productDto.getDescription())) {
      throw new HandledException(ExceptionType.PRODUCT_REQUIRED_DESCRIPTION);

    }

  }


}
