package com.myapps.advancedapijava.modules.product.util;

import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductUtil {
  private ProductUtil() {
  }

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


  static public Product updateEntityValues(
    Product oldEntity, Product newEntity, Boolean ignoreIds, Boolean ignoreNulls
  ) {
    Product resEntity = new Product();
    Long id;
    String name;
    String description;
    Float price;

    if (!ignoreIds) {
      id = newEntity.getId();
    } else {
      id = oldEntity.getId();
    }
    if (!ignoreNulls) {
      name = newEntity.getName();
      description = newEntity.getDescription();
      price = newEntity.getPrice();
    } else {
      name = Optional.ofNullable(newEntity.getName()).orElse(oldEntity.getName());
      description = Optional.ofNullable(newEntity.getDescription()).orElse(oldEntity.getDescription());
      price = Optional.ofNullable(newEntity.getPrice()).orElse(oldEntity.getPrice());
    }

    resEntity.setId(id);
    resEntity.setName(name);
    resEntity.setDescription(description);
    resEntity.setPrice(price);
    return resEntity;
  }

  static public Product updateEntityNoId(Product oldEntity, Product newEntity) {
    return updateEntityValues(oldEntity, newEntity, true, false);
  }

  static public Product updateEntityNoIdNotNull(Product oldEntity, Product newEntity) {
    return updateEntityValues(oldEntity, newEntity, true, true);
  }

  static public Product updateEntityFull(Product oldEntity, Product newEntity) {
    return updateEntityValues(oldEntity, newEntity, false, false);
  }

}
