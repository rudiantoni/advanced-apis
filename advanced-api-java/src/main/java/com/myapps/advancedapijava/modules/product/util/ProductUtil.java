package com.myapps.advancedapijava.modules.product.util;

import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductUtil {
  private ProductUtil() {
  }

  // Convert: To DTOs
  static public ProductDto toDto(Product entity) {
    return ProductDto.builder()
      .id(entity.getId())
      .name(entity.getName())
      .description(entity.getDescription())
      .price(entity.getPrice())
      .build();
  }

  static public List<ProductDto> toDtoList(List<Product> entityList) {
    List<ProductDto> dtoList = new ArrayList<>();
    entityList.forEach(it -> {
      ProductDto dto = toDto(it);
      dtoList.add(dto);
    });
    return dtoList;
  }

  // Convert: To Entities
  static private Product toEntityBase(ProductDto dto, Boolean ignoreIds) {
    Product entity = new Product();
    if (!ignoreIds) {
      entity.setId(dto.getId());
    }
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setPrice(dto.getPrice());
    return entity;
  }

  static public Product toEntity(ProductDto dto) {
    return toEntityBase(dto, false);
  }

  static public Product toEntityNoId(ProductDto dto) {
    return toEntityBase(dto, true);
  }


  static private List<Product> toEntityListBase(List<ProductDto> dtoList, Boolean ignoreIds) {
    List<Product> entityList = new ArrayList<>();
    dtoList.forEach(it -> {
      Product entity = toEntityBase(it, ignoreIds);
      entityList.add(entity);
    });
    return entityList;
  }

  static public List<Product> toEntityList(List<ProductDto> dtoList) {
    return toEntityListBase(dtoList, false);
  }

  static public List<Product> toEntityListNoId(List<ProductDto> dtoList) {
    return toEntityListBase(dtoList, true);
  }

  // Convert: Entities Update
  static public Product updateEntityBase(
    Product oldEntity, Product newEntity, Boolean ignoreIds, Boolean ignoreNulls
  ) {
    Product resEntity = new Product();

    if (!ignoreIds) {
      resEntity.setId(newEntity.getId());
    } else {
      resEntity.setId(oldEntity.getId());
    }
    if (!ignoreNulls) {
      resEntity.setName(newEntity.getName());
      resEntity.setDescription(newEntity.getDescription());
      resEntity.setPrice(newEntity.getPrice());
    } else {
      resEntity.setName(Optional.ofNullable(newEntity.getName()).orElse(oldEntity.getName()));
      resEntity.setDescription(Optional.ofNullable(newEntity.getDescription()).orElse(oldEntity.getDescription()));
      resEntity.setPrice(Optional.ofNullable(newEntity.getPrice()).orElse(oldEntity.getPrice()));
    }

    return resEntity;
  }

  static public Product updateEntityNoId(Product oldEntity, Product newEntity) {
    return updateEntityBase(oldEntity, newEntity, true, false);
  }

  static public Product updateEntityNoIdNotNull(Product oldEntity, Product newEntity) {
    return updateEntityBase(oldEntity, newEntity, true, true);
  }

  static public Product updateEntityFull(Product oldEntity, Product newEntity) {
    return updateEntityBase(oldEntity, newEntity, false, false);
  }

}
