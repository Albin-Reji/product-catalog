package com.albin.productcatalog.repository;

import com.albin.productcatalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
