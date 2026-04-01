package com.albin.productcatalog.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
@Builder
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
