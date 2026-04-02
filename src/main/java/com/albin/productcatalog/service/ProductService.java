package com.albin.productcatalog.service;

import com.albin.productcatalog.dto.ProductRequestDTO;
import com.albin.productcatalog.dto.ProductResponseDTO;
import com.albin.productcatalog.dto.ProductUpdateRequestDto;
import com.albin.productcatalog.exception.ProducerAlreadyExists;
import com.albin.productcatalog.exception.ProducerNotFoundException;
import com.albin.productcatalog.exception.ProductAlreadyExist;
import com.albin.productcatalog.exception.ProductNotFound;
import com.albin.productcatalog.model.Producer;
import com.albin.productcatalog.model.Product;
import com.albin.productcatalog.model.ProductAttribute;
import com.albin.productcatalog.repository.ProducerRepository;
import com.albin.productcatalog.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service layer for <strong>Product</strong> business logic.
 * <p>
 * Manages the full lifecycle of products: creation (with producer validation
 * and attribute mapping), paginated listing, lookup by name, partial updates,
 * and deletion. All mutating operations are transactional.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;

    /**
     * Creates a new product linked to an existing producer.
     * <p>
     * Validates that the referenced producer exists and that no product
     * with the same name is already registered. Attribute key-value pairs
     * from the DTO are converted into {@link ProductAttribute} entities.
     * </p>
     *
     * @param productDto the incoming product data
     * @return the persisted product as a response DTO
     * @throws ProducerNotFoundException if the referenced producer does not exist
     * @throws ProducerAlreadyExists     if a product with the same name already exists
     */
    public ProductResponseDTO createProduct(ProductRequestDTO productDto) {

        // Verify that the referenced producer exists in the database
        Producer producer = producerRepository.findByProducerId(productDto.getProducerId())
                .orElseThrow(() -> new ProducerNotFoundException("Producer Not Found.."));

        // Guard: reject duplicate product names
        if (productRepository.findByProductName(productDto.getProductName()).orElse(null) != null) {
            throw new ProducerAlreadyExists("Product Already Exist");
        }

        // Build the product entity from the DTO
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setProducer(producer);
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());

        // Convert each attribute key-value pair into a ProductAttribute entity
        // and establish the bidirectional relationship
        productDto.getAttributes().forEach((k, v) -> {
            ProductAttribute attribute = new ProductAttribute();
            attribute.setKey(k);
            attribute.setValue(v);
            attribute.setProduct(product); // set owning side of the relationship
            product.getAttributes().add(attribute);
        });

        return toProductResponseDto(productRepository.save(product));
    }

    /**
     * Returns a paginated list of all products.
     *
     * @param page           zero-based page index
     * @param sizeOfEachPage number of products per page
     * @return a {@link Page} of product response DTOs
     */
    public Page<ProductResponseDTO> getAllProduct(int page, int sizeOfEachPage) {
        Pageable pageable = PageRequest.of(page, sizeOfEachPage);
        Page<Product> pages = productRepository.findAll(pageable);
        return pages
                .map(this::toProductResponseDto);
    }

    /**
     * Converts a {@link Product} entity (with its attributes and producer)
     * into a flat {@link ProductResponseDTO}.
     *
     * @param product the entity to convert
     * @return the corresponding response DTO
     */
    private ProductResponseDTO toProductResponseDto(Product product) {
        // Flatten the list of ProductAttribute entities into a simple Map
        Map<String, String> attributeMap = new HashMap<>();
        product.getAttributes().forEach(attr ->
                attributeMap.put(attr.getKey(), attr.getValue())
        );

        return ProductResponseDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .category(product.getCategory())
                .attributes(attributeMap)
                .producerId(product.getProducer().getProducerId())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

    }

    /**
     * Retrieves a single product by its unique display name.
     *
     * @param productName the product name to look up
     * @return the matching product as a response DTO
     * @throws ProductNotFound if no product with the given name exists
     */
    public ProductResponseDTO getProductByName(String productName) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFound("Product Not Found"));
        return toProductResponseDto(product);
    }

    /**
     * Deletes a product by its UUID.
     * <p>
     * First verifies that the product exists to provide a meaningful error
     * message, then performs the deletion within a transaction.
     * </p>
     *
     * @param productId UUID of the product to delete
     * @throws ProductNotFound if no product with the given ID exists
     */
    @Transactional
    public void deleteProductById(String productId) {
        productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFound("Product With Id " + productId + " Not Found"));
        productRepository.deleteByProductId(productId);
    }

    /**
     * Partially updates an existing product.
     * <p>
     * Only non-null / non-blank fields from the update DTO are applied.
     * When new attributes are provided, the existing attribute list is
     * cleared first (orphan removal handles DB cleanup), then new or
     * matching attributes are (re-)added.
     * </p>
     *
     * @param productId  UUID of the product to update
     * @param productDto the partial update payload
     * @return the updated product as a response DTO
     * @throws ProductNotFound if no product with the given ID exists
     */
    @Transactional
    public ProductResponseDTO updateProduct(String productId, ProductUpdateRequestDto productDto) {

        // Fetch the existing product or throw if not found
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFound("Product With Id: " + productId + " Not Found"));

        // Apply non-null scalar fields
        if (productDto.getProductName() != null && !productDto.getProductName().isBlank()) {
            product.setProductName(productDto.getProductName());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getCategory() != null) {
            product.setCategory(productDto.getCategory());
        }

        // Clear existing attributes – orphanRemoval = true will delete
        // removed entities from the database automatically
        product.getAttributes().clear();

        // Rebuild the attribute list from the incoming DTO map
        if (productDto.getAttributes() != null) {
            productDto.getAttributes().forEach((k, v) -> {

                // Check if an attribute with the same key already exists
                Optional<ProductAttribute> existing = product.getAttributes()
                        .stream()
                        .filter(attr -> attr.getKey().equals(k))
                        .findFirst();

                if (existing.isPresent()) {
                    // Update the value of the existing attribute
                    existing.get().setValue(v);
                    product.getAttributes().add(existing.get());
                } else {
                    // Create a brand-new attribute entity
                    ProductAttribute newAttr = new ProductAttribute();
                    newAttr.setKey(k);
                    newAttr.setValue(v);
                    newAttr.setProduct(product);
                    product.getAttributes().add(newAttr);
                }
            });

        }
        return toProductResponseDto(productRepository.save(product));
    }
}