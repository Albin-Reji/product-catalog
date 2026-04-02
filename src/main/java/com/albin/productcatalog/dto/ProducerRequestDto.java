package com.albin.productcatalog.dto;

import lombok.Data;

/**
 * Data Transfer Object for <strong>creating</strong> a new producer.
 * <p>
 * Carries the client-supplied fields required to register a producer.
 * The {@code producerId} is generated server-side and is therefore not
 * part of this request DTO.
 * </p>
 */
@Data
public class ProducerRequestDto {

    /** Display name of the producer (must be unique). */
    private String producerName;

    /** Physical or mailing address. */
    private String address;

    /** Contact email address. */
    private String email;

    /** Contact phone number. */
    private String phone;
}
