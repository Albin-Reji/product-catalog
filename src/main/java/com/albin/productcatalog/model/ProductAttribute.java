package com.albin.productcatalog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_attributes")
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attribute_id")
    private String attributeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
                foreignKey = @ForeignKey(name = "fk_attribute_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(name = "attr_key")
    private String key;

    @Column(name = "attr_value")
    private String value;


}
