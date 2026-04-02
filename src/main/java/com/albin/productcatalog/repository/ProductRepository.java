package com.albin.productcatalog.repository;

import com.albin.productcatalog.model.Product;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Product} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD and pagination
 * support, with additional custom query methods for product-specific lookups.
 * </p>
 */
public interface ProductRepository extends JpaRepository<Product, String> {

	/**
	 * Finds a product by its unique display name.
	 *
	 * @param productName the product name to search for
	 * @return an {@link Optional} containing the product, or empty if not found
	 */
	Optional<Product> findByProductName(@NotBlank String productName);

	/**
	 * Deletes a product by its UUID.
	 * <p>Must be called within a transactional context.</p>
	 *
	 * @param productId the UUID of the product to delete
	 */
	void deleteByProductId(String productId);

	/**
	 * Finds a product by its UUID.
	 *
	 * @param productId the product's unique identifier
	 * @return an {@link Optional} containing the product, or empty if not found
	 */
	Optional<Product> findByProductId(String productId);
}
