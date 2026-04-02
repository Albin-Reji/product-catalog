package com.albin.productcatalog.repository;

import com.albin.productcatalog.model.Product;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
	Optional<Product> findByProductName(@NotBlank String productName);

	void deleteByProductId(String productId);


}
