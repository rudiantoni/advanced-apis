package com.myapps.bavariamunich.mapper;

import com.myapps.bavariamunich.dto.ProductDto;
import com.myapps.bavariamunich.dto.ProductUpdateDto;
import com.myapps.bavariamunich.dto.ProductWriteDto;
import com.myapps.bavariamunich.entity.Product;
import com.myapps.bavariamunich.util.JsonUtil;

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

    public static Product toEntity(ProductWriteDto dto) {
        if (dto == null) {
            return null;
        }
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setReference(dto.getReference());
        entity.setStockQuantity(dto.getStockQuantity());
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }

    public static void replaceEntity(Product target, ProductWriteDto source) {
        if (target == null || source == null) {
            return;
        }
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setReference(source.getReference());
        target.setStockQuantity(source.getStockQuantity());
        target.setImageUrl(source.getImageUrl());
    }

    public static void updateEntity(Product target, ProductUpdateDto source) {
        if (target == null || source == null) {
            return;
        }
        JsonUtil.applyIfDefined(source.getName(), target::setName);
        JsonUtil.applyIfDefined(source.getDescription(), target::setDescription);
        JsonUtil.applyIfDefined(source.getPrice(), target::setPrice);
        JsonUtil.applyIfDefined(source.getReference(), target::setReference);
        JsonUtil.applyIfDefined(source.getStockQuantity(), target::setStockQuantity);
        JsonUtil.applyIfDefined(source.getImageUrl(), target::setImageUrl);
    }
}
