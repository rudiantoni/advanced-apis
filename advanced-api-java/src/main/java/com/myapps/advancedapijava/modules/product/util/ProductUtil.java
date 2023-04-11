package com.myapps.advancedapijava.modules.product.util;

import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.entity.Product;
import com.myapps.advancedapijava.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ProductUtil {
  private ProductUtil() {
  }

  static public ProductDto toDto(Product entity) {
    return ProductDto.builder()
      .id(entity.getId())
      .name(entity.getName())
      .description(entity.getDescription())
      .price(entity.getPrice())
      .amount(entity.getAmount())
      .build();
  }

  static public Product toEntity(ProductDto dto) {
    return toEntity(dto, false);
  }

  static public List<ProductDto> toDtoList(List<Product> entityList) {
    List<ProductDto> dtoList = new ArrayList<>();
    entityList.forEach(it -> {
      ProductDto dto = toDto(it);
      dtoList.add(dto);
    });
    return dtoList;
  }

  static public Product toEntityNoId(ProductDto dto) {
    return toEntity(dto, true);
  }

  static public Product toEntity(ProductDto dto, Boolean ignoreIds) {
    Product entity = new Product();
    if (!ignoreIds) {
      entity.setId(dto.getId());
    }
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setPrice(dto.getPrice());
    entity.setAmount(dto.getAmount());
    return entity;
  }

  /*
  static public List<Product> toEntityList(List<ProductDto> dtoList, Boolean ignoreIds) {
    List<Product> entityList = new ArrayList<>();
    dtoList.forEach(it -> {
      Product entity = toEntity(it, ignoreIds);
      entityList.add(entity);
    });
    return entityList;
  }
   */

  /*
  static public List<Product> toEntityListNoId(List<ProductDto> dtoList) {
    return toEntityList(dtoList, true);
  }
   */

  static public Product updateEntityValues(
    Product oldEntity, Product newEntity, Boolean ignoreIds, Boolean ignoreNulls
  ) {
    Product resEntity = new Product();
    Long id;
    String name;
    String description;
    Float price;
    Integer amount;

    if (!ignoreIds) {
      id = newEntity.getId();
    } else {
      id = oldEntity.getId();
    }
    if (!ignoreNulls) {
      name = newEntity.getName();
      description = newEntity.getDescription();
      price = newEntity.getPrice();
      amount = newEntity.getAmount();
    } else {
      name = Util.getOrDefault(newEntity.getName(), oldEntity.getName());
      description = Util.getOrDefault(newEntity.getDescription(), oldEntity.getDescription());
      price = Util.getOrDefault(newEntity.getPrice(), oldEntity.getPrice());
      amount = Util.getOrDefault(newEntity.getAmount(), oldEntity.getAmount());
    }

    resEntity.setId(id);
    resEntity.setName(name);
    resEntity.setDescription(description);
    resEntity.setPrice(price);
    resEntity.setAmount(amount);
    return resEntity;
  }

  static public Product updateEntityNoId(Product oldEntity, Product newEntity) {
    return updateEntityValues(oldEntity, newEntity, true, false);
  }

  static public Product updateEntityNoIdNotNull(Product oldEntity, Product newEntity) {
    return updateEntityValues(oldEntity, newEntity, true, true);
  }

}
