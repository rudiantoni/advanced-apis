package com.myapps.bavariamunich.mapper;

import com.myapps.bavariamunich.dto.ProductDto;
import com.myapps.bavariamunich.entity.Product;

public class ProductMapper {
    public static ProductDto toDto(Product entity) {
        if (entity == null) {
            return null;
        }

        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setReference(entity.getReference());
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setImageUrl(entity.getImageUrl());

        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }

        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setReference(dto.getReference());
        entity.setStockQuantity(dto.getStockQuantity());
        entity.setImageUrl(dto.getImageUrl());

        return entity;
    }

    public static void replaceEntity(Product target, ProductDto source) {
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setReference(source.getReference());
        target.setStockQuantity(source.getStockQuantity());
        target.setImageUrl(source.getImageUrl());
    }
}
