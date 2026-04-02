package com.albin.productcatalog.controller;

import com.albin.productcatalog.dto.ProducerRequestDto;
import com.albin.productcatalog.dto.ProducerResponseDto;
import com.albin.productcatalog.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for <strong>Producer</strong> operations.
 * <p>
 * Base path: {@code /api/v1/producers}
 * </p>
 * Provides endpoints to create, and retrieve producers by ID or name.
 */
@RestController
@RequestMapping("/api/v1/producers")
@RequiredArgsConstructor
public class ProducerController {

    private  final ProducerService producerService;

    /**
     * Creates a new producer.
     *
     * @param producer the producer details from the request body
     * @return the created producer with HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<ProducerResponseDto> createProducer(@RequestBody ProducerRequestDto producer){
        return ResponseEntity.status(HttpStatus.CREATED).body(producerService.createProducer(producer));
    }

    /**
     * Retrieves a producer by its unique ID.
     *
     * @param producerId UUID of the producer
     * @return the matching producer with HTTP 200 (OK)
     */
    @GetMapping("/id/{producerId}")
    public ResponseEntity<ProducerResponseDto>  getProducerById(@PathVariable("producerId") String producerId ){
        return ResponseEntity.status(HttpStatus.OK).body(producerService.getProducerById(producerId));
    }

    /**
     * Retrieves a producer by its unique name.
     *
     * @param producerName name of the producer
     * @return the matching producer with HTTP 200 (OK)
     */
    @GetMapping("/{producerName}")
    public ResponseEntity<ProducerResponseDto>  getProducerByName(@PathVariable("producerName") String producerName ){
        return ResponseEntity.status(HttpStatus.OK).body(producerService.getProducerByName(producerName));
    }

}
