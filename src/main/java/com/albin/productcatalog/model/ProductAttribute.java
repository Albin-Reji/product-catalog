package com.albin.productcatalog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * JPA entity representing a single <strong>key-value attribute</strong> of a
 * {@link Product} (e.g. "color" → "red", "weight" → "1.5kg").
 * <p>
 * Maps to the {@code product_attributes} table. Each attribute belongs to
 * exactly one product via a Many-to-One relationship.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_attributes")
public class ProductAttribute {

    /** Primary key – auto-generated UUID string. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attribute_id")
    private String attributeId;

    /**
     * The product this attribute belongs to.
     * <p>
     * Lazy-fetched. DB-level cascade delete ensures cleanup when the
     * parent product is removed.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
                foreignKey = @ForeignKey(name = "fk_attribute_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    /** Attribute name / key (e.g. "color", "size"). Stored as {@code attr_key}. */
    @Column(name = "attr_key")
    private String key;

    /** Attribute value (e.g. "red", "XL"). Stored as {@code attr_value}. */
    @Column(name = "attr_value")
    private String value;


}
