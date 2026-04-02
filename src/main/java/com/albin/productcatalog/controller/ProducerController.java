package com.albin.productcatalog.controller;

import com.albin.productcatalog.dto.ProducerRequestDto;
import com.albin.productcatalog.dto.ProducerResponseDto;
import com.albin.productcatalog.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/producers")
@RequiredArgsConstructor
public class ProducerController {

    private  final ProducerService producerService;

    @PostMapping
    public ResponseEntity<ProducerResponseDto> createProducer(@RequestBody ProducerRequestDto producer){
        return ResponseEntity.status(HttpStatus.CREATED).body(producerService.createProducer(producer));
    }
    @GetMapping("/id/{producerId}")
    public ResponseEntity<ProducerResponseDto>  getProducerById(@PathVariable("producerId") String producerId ){
        return ResponseEntity.status(HttpStatus.CREATED).body(producerService.getProducerById(producerId));
    }
    @GetMapping("/{producerName}")
    public ResponseEntity<ProducerResponseDto>  getProducerByName(@PathVariable("producerName") String producerName ){
        return ResponseEntity.status(HttpStatus.CREATED).body(producerService.getProducerByName(producerName));
    }

}
