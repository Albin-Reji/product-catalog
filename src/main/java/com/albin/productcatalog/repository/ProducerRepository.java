package com.albin.productcatalog.repository;

import com.albin.productcatalog.model.Producer;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Producer} entities.
 * <p>
 * Provides standard CRUD operations inherited from {@link JpaRepository}
 * plus custom finder methods for lookup by ID and name.
 * </p>
 */
@Repository
public interface ProducerRepository extends JpaRepository<Producer, String> {

    /**
     * Finds a producer by its UUID.
     *
     * @param producerId the producer's unique identifier
     * @return an {@link Optional} containing the producer, or empty if not found
     */
    Optional<Producer> findByProducerId(@NotNull String producerId);

    /**
     * Finds a producer by its unique business name.
     *
     * @param producerName the producer's name
     * @return an {@link Optional} containing the producer, or empty if not found
     */
    Optional<Producer> findByProducerName(String producerName);
}
