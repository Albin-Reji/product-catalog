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

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO productDto) {
        /*
         * check producer is in DB
         */
        Producer producer = producerRepository.findByProducerId(productDto.getProducerId())
                .orElseThrow(() -> new ProducerNotFoundException("Producer Not Found.."));

        if (productRepository.findByProductName(productDto.getProductName()).orElse(null) != null) {
            throw new ProducerAlreadyExists("Product Already Exist");
        }

        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setProducer(producer);
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());

        productDto.getAttributes().forEach((k, v) -> {
            ProductAttribute attribute = new ProductAttribute();
            attribute.setKey(k);
            attribute.setValue(v);
            attribute.setProduct(product);
            product.getAttributes().add(attribute);
        });
        return toProductResponseDto(productRepository.save(product));
    }

    public Page<ProductResponseDTO> getAllProduct(int page, int sizeOfEachPage) {
        Pageable pageable = PageRequest.of(page, sizeOfEachPage);
        Page<Product> pages = productRepository.findAll(pageable);
        return pages
                .map(this::toProductResponseDto);
    }

    private ProductResponseDTO toProductResponseDto(Product product) {
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

    public ProductResponseDTO getProductByName(String productName) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFound("Product Not Found"));
        return toProductResponseDto(product);
    }

    @Transactional
    public void deleteProductById(String productId) {
        productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFound("Product With Id " + productId + " Not Found"));
        productRepository.deleteByProductId(productId);
    }

    @Transactional
    public ProductResponseDTO updateProduct(String productId, ProductUpdateRequestDto productDto) {

        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFound("Product With Id: " + productId + " Not Found"));

        if (productDto.getProductName() != null && !productDto.getProductName().isBlank()) {
            product.setProductName(productDto.getProductName());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getCategory() != null) {
            product.setCategory(productDto.getCategory());
        }
        product.getAttributes().clear();

        if (productDto.getAttributes() != null) {
            productDto.getAttributes().forEach((k, v) -> {

                Optional<ProductAttribute> existing = product.getAttributes()
                        .stream()
                        .filter(attr -> attr.getKey().equals(k))
                        .findFirst();

                if (existing.isPresent()) {

                    existing.get().setValue(v);
                    product.getAttributes().add(existing.get());
                } else {
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