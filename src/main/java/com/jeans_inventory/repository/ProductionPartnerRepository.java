package com.jeans_inventory.repository;

import com.jeans_inventory.entity.ProductionPartner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionPartnerRepository extends JpaRepository<ProductionPartner, Long> {
}
