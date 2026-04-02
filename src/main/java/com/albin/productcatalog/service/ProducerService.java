package com.albin.productcatalog.service;

import com.albin.productcatalog.dto.ProducerRequestDto;
import com.albin.productcatalog.dto.ProducerResponseDto;
import com.albin.productcatalog.exception.ProducerAlreadyExists;
import com.albin.productcatalog.exception.ProducerNotFoundException;
import com.albin.productcatalog.model.Producer;
import com.albin.productcatalog.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;

    @Transactional
    public ProducerResponseDto createProducer(ProducerRequestDto producerDto) {

        producerRepository.findByProducerName(producerDto.getProducerName())
                .ifPresent(p -> {
                    throw new ProducerAlreadyExists("Producer Already Exists");
                });

        Producer producer = Producer.builder()
                .producerName(producerDto.getProducerName())
                .address(producerDto.getAddress())
                .email(producerDto.getEmail())
                .phone(producerDto.getPhone())
                .build();

        return toProducerResponseDto(producerRepository.save(producer));
    }

    private ProducerResponseDto toProducerResponseDto(Producer producer) {
        return ProducerResponseDto.builder()
                .producerId(producer.getProducerId())
                .producerName(producer.getProducerName())
                .address(producer.getAddress())
                .email(producer.getEmail())
                .phone(producer.getPhone())
                .build();
    }

    public ProducerResponseDto getProducerById(String producerId) {
        Producer producer=producerRepository.findByProducerId(producerId)
                .orElseThrow(()-> new ProducerNotFoundException("Producer Not Found With Id: "+producerId));
        return toProducerResponseDto(producer);
    }

    public ProducerResponseDto getProducerByName(String producerName) {
       Producer producer=producerRepository.findByProducerName(producerName)
                .orElseThrow(()->new ProducerNotFoundException("Producer Not Found With Name"+ producerName));
        return toProducerResponseDto(producer);
    }
}