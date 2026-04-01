package com.albin.productcatalog.controller;

import com.albin.productcatalog.dto.ProducerRequestDto;
import com.albin.productcatalog.dto.ProducerResponseDto;
import com.albin.productcatalog.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/producers")
@RequiredArgsConstructor
public class ProducerController {

    private  final ProducerService producerService;

    @PostMapping
    public ResponseEntity<ProducerResponseDto> createProducer(@RequestBody ProducerRequestDto producer){
        return ResponseEntity.status(HttpStatus.CREATED).body(producerService.createProducer(producer));
    }
}
