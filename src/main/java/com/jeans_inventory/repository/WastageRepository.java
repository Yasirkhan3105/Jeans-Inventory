package com.jeans_inventory.repository;

import com.jeans_inventory.entity.Wastage;
import com.jeans_inventory.entity.WastageStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WastageRepository extends JpaRepository<Wastage, Long> {

    List<Wastage> findAllByStage(WastageStage stage);
}
