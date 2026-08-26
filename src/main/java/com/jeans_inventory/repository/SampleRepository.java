package com.jeans_inventory.repository;

import com.jeans_inventory.entity.Sample;
import com.jeans_inventory.entity.SampleLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<Sample, Long> {

    Optional<Sample> findByStyleIdAndLocation(Long styleId, SampleLocation location);
}
