package com.jeans_inventory.repository;

import com.jeans_inventory.entity.StylePricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StylePricingRepository extends JpaRepository<StylePricing, Long> {

    Optional<StylePricing> findByStyleId(Long styleId);
}
