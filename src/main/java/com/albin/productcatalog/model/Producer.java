package com.albin.productcatalog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a <strong>Producer</strong> (manufacturer / supplier).
 * <p>
 * Maps to the {@code producers} table. Each producer can be associated with
 * many {@link Product} records via the product's foreign-key relationship.
 * </p>
 *
 * <p>Lombok annotations generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} – getters, setters, toString, equals, hashCode</li>
 *   <li>{@code @Builder} – fluent builder pattern</li>
 *   <li>{@code @AllArgsConstructor / @NoArgsConstructor} – constructors</li>
 * </ul>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="producers")
@Data
public class Producer {

    /** Primary key – auto-generated UUID string. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "producer_id")
    private String producerId;

    /** Unique, non-nullable business name of the producer. */
    @Column(name = "producer_name", unique = true, nullable = false)
    private String producerName;

    /** Physical or mailing address of the producer. */
    private String address;

    /** Contact email address. */
    private String email;

    /** Contact phone number. */
    private String phone;

}
