package com.albin.productcatalog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProducerResponseDto {

    private String producerId;
    private String producerName;
    private String address;
    private String email;
    private String phone;


}
