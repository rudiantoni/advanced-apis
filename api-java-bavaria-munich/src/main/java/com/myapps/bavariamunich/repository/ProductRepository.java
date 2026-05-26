package com.myapps.bavariamunich.repository;

import com.myapps.bavariamunich.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
