package com.myapps.bavariamunich.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(schema = "public", name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "public.product_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "reference", length = 128)
    private String reference;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    public Product() {
    }

    public Product(
            Long id,
            String name,
            String description,
            BigDecimal price,
            String reference,
            Integer stockQuantity,
            String imageUrl
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.reference = reference;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    public Product copy() {
        return new Product(id, name, description, price, reference, stockQuantity, imageUrl);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
