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

/**
 * Service layer for <strong>Producer</strong> business logic.
 * <p>
 * Handles creation and retrieval of producers, enforcing uniqueness
 * constraints on the producer name before persistence.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;

    /**
     * Creates a new producer after verifying that no producer with the
     * same name already exists.
     *
     * @param producerDto the incoming producer data
     * @return the persisted producer as a response DTO
     * @throws ProducerAlreadyExists if a producer with the same name exists
     */
    @Transactional
    public ProducerResponseDto createProducer(ProducerRequestDto producerDto) {

        // Guard: reject duplicate producer names
        producerRepository.findByProducerName(producerDto.getProducerName())
                .ifPresent(p -> {
                    throw new ProducerAlreadyExists("Producer Already Exists");
                });

        // Build the entity from the DTO and persist it
        Producer producer = Producer.builder()
                .producerName(producerDto.getProducerName())
                .address(producerDto.getAddress())
                .email(producerDto.getEmail())
                .phone(producerDto.getPhone())
                .build();

        return toProducerResponseDto(producerRepository.save(producer));
    }

    /**
     * Converts a {@link Producer} entity to a {@link ProducerResponseDto}.
     *
     * @param producer the entity to convert
     * @return the corresponding response DTO
     */
    private ProducerResponseDto toProducerResponseDto(Producer producer) {
        return ProducerResponseDto.builder()
                .producerId(producer.getProducerId())
                .producerName(producer.getProducerName())
                .address(producer.getAddress())
                .email(producer.getEmail())
                .phone(producer.getPhone())
                .build();
    }

    /**
     * Retrieves a producer by its UUID.
     *
     * @param producerId the producer's unique identifier
     * @return the matching producer as a response DTO
     * @throws ProducerNotFoundException if no producer with the given ID exists
     */
    public ProducerResponseDto getProducerById(String producerId) {
        Producer producer=producerRepository.findByProducerId(producerId)
                .orElseThrow(()-> new ProducerNotFoundException("Producer Not Found With Id: "+producerId));
        return toProducerResponseDto(producer);
    }

    /**
     * Retrieves a producer by its unique business name.
     *
     * @param producerName the producer's name
     * @return the matching producer as a response DTO
     * @throws ProducerNotFoundException if no producer with the given name exists
     */
    public ProducerResponseDto getProducerByName(String producerName) {
       Producer producer=producerRepository.findByProducerName(producerName)
                .orElseThrow(()->new ProducerNotFoundException("Producer Not Found With Name: "+ producerName));
        return toProducerResponseDto(producer);
    }
}