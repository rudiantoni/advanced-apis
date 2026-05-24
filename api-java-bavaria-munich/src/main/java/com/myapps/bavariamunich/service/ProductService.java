package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<ProductDto> products = new ArrayList<>();

    private Long repoGetProductNextId() {
        return (long) (this.products.size() + 1);
    }

    private List<ProductDto> repoFindProducts() {
        return this.products;
    }

    private ProductDto repoSaveProduct(ProductDto productDto) {
        ProductDto newProduct = new ProductDto(
                repoGetProductNextId(),
                productDto.getDescription()
        );
        products.add(newProduct);
        return newProduct;
    }


    public List<ProductDto> readProducts() {
        return repoFindProducts();
    }

    public ProductDto createProduct(ProductDto productDto) {
        return repoSaveProduct(productDto);
    }

}
