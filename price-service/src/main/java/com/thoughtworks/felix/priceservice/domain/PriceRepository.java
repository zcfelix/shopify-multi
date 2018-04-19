package com.thoughtworks.felix.priceservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findAllByProductId(Long productId);

    Optional<Price> findTopByProductIdOrderByCreatedAtDesc(Long productId);
}
