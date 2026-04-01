package com.albin.productcatalog.repository;

import com.albin.productcatalog.model.Producer;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, String> {
    Optional<Producer> findByProducerId(@NotNull String producerId);
}
