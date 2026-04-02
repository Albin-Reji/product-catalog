package com.albin.productcatalog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a <strong>Product</strong> in the catalog.
 * <p>
 * Maps to the {@code products} table with database-level indexes on
 * {@code product_id} and {@code producer_id} for faster lookups.
 * </p>
 *
 * <h3>Relationships</h3>
 * <ul>
 *   <li>{@link #producer} – Many-to-One link to the {@link Producer} who
 *       manufactures this product. Cascade-deletes at the DB level.</li>
 *   <li>{@link #attributes} – One-to-Many link to {@link ProductAttribute}
 *       entries (e.g. color, size). Orphaned attributes are automatically removed.</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products",
        indexes ={
        @Index(name="idx_product_id", columnList ="product_id"),
        @Index(name="idx_producer_id", columnList ="producer_id")
        }
)
public class Product {

    /** Primary key – auto-generated UUID string. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private String productId;

    /**
     * The producer (manufacturer) of this product.
     * <p>
     * Lazy-fetched to avoid loading the full producer graph on every product query.
     * {@code @OnDelete(CASCADE)} ensures that deleting a producer in the DB
     * also removes all associated products.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_product_producer"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Producer producer;

    /** Unique product display name (business key). */
    @Column(nullable = false, unique=true )
    private String productName;

    /** Retail price of the product. Uses {@link BigDecimal} for precision. */
    @Column(nullable = false)
    private BigDecimal price;

    /** Optional product category (e.g. "Electronics", "Clothing"). */
    private String category;

    /**
     * Dynamic key-value attributes for the product (e.g. color, weight).
     * <p>
     * {@code CascadeType.ALL} persists/merges/removes child attributes with
     * the parent product. {@code orphanRemoval = true} deletes attributes
     * that are removed from this list.
     * </p>
     */
    @OneToMany(mappedBy = "product",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<ProductAttribute> attributes = new ArrayList<>();

    /** Timestamp set automatically when the product is first persisted. */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /** Timestamp updated automatically on every modification. */
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
