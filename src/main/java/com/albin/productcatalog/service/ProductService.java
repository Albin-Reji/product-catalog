package com.albin.productcatalog.service;

import com.albin.productcatalog.dto.ProductRequestDTO;
import com.albin.productcatalog.dto.ProductResponseDTO;
import com.albin.productcatalog.exception.ProducerNotFoundException;
import com.albin.productcatalog.model.Producer;
import com.albin.productcatalog.model.Product;
import com.albin.productcatalog.model.ProductAttribute;
import com.albin.productcatalog.repository.ProducerRepository;
import com.albin.productcatalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO productDto) {
     /*
     * check producer is in DB
     */
        Producer producer=producerRepository.findByProducerId(productDto.getProducerId())
                .orElseThrow(()->  new ProducerNotFoundException("Producer Not Found.."));

        Product product=new Product();
        product.setProductName(productDto.getProductName());
        product.setProducer(producer);
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());

        productDto.getAttributes().forEach((k, v)->{
            ProductAttribute attribute=new ProductAttribute();
            attribute.setKey(k);attribute.setValue(v);attribute.setProduct(product);
            product.getAttributes().add(attribute);
        });
        return toProductResponseDto(productRepository.save(product));
    }

    private ProductResponseDTO toProductResponseDto(Product product) {
        Map<String, String> attributeMap=new HashMap<>();
        product.getAttributes().forEach( attr ->
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
}
