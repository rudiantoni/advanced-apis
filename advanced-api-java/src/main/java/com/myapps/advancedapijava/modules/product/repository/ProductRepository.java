package com.myapps.advancedapijava.modules.product.repository;

import com.myapps.advancedapijava.modules.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
