package com.albin.productcatalog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="producers")
@Data
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "producer_id")
    private String producerId;

    @Column(name = "producer_name", unique = true, nullable = false)
    private String producerName;

    private String address;

    private String email;

    private String phone;

}
