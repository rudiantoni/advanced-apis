package com.myapps.bavariamunich.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;

public class ProductUpdateDto {

    private JsonNullable<String> name = JsonNullable.undefined();
    private JsonNullable<String> description = JsonNullable.undefined();
    private JsonNullable<BigDecimal> price = JsonNullable.undefined();
    private JsonNullable<String> reference = JsonNullable.undefined();
    private JsonNullable<Integer> stockQuantity = JsonNullable.undefined();
    private JsonNullable<String> imageUrl = JsonNullable.undefined();

    public ProductUpdateDto() {
    }

    public JsonNullable<String> getName() {
        return name;
    }

    public void setName(JsonNullable<String> name) {
        this.name = name;
    }

    public JsonNullable<String> getDescription() {
        return description;
    }

    public void setDescription(JsonNullable<String> description) {
        this.description = description;
    }

    public JsonNullable<BigDecimal> getPrice() {
        return price;
    }

    public void setPrice(JsonNullable<BigDecimal> price) {
        this.price = price;
    }

    public JsonNullable<String> getReference() {
        return reference;
    }

    public void setReference(JsonNullable<String> reference) {
        this.reference = reference;
    }

    public JsonNullable<Integer> getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(JsonNullable<Integer> stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public JsonNullable<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(JsonNullable<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

}
