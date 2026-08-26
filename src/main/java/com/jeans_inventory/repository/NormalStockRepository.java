package com.jeans_inventory.repository;

import com.jeans_inventory.entity.Location;
import com.jeans_inventory.entity.NormalStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NormalStockRepository extends JpaRepository<NormalStock, Long> {

    Optional<NormalStock> findByStyle_IdAndLocation(
            Long styleId,
            Location location
    );

    List<NormalStock> findAllByLocation(
            Location location
    );
}