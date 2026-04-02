package com.albin.productcatalog.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object returned to the client after a producer operation.
 * <p>
 * Includes the server-generated {@code producerId} along with all
 * producer details.
 * </p>
 */
@Data
@Builder
public class ProducerResponseDto {

    /** Server-generated UUID of the producer. */
    private String producerId;

    /** Display name of the producer. */
    private String producerName;

    /** Physical or mailing address. */
    private String address;

    /** Contact email address. */
    private String email;

    /** Contact phone number. */
    private String phone;

}
