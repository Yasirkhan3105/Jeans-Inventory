package com.jeans_inventory.repository;

import com.jeans_inventory.entity.ShopAssortment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopAssortmentRepository extends JpaRepository<ShopAssortment, Long> {

    Optional<ShopAssortment> findByStyleIdAndSize(Long styleId, Integer size);
}
