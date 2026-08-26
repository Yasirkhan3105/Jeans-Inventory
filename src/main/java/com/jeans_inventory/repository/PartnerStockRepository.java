package com.jeans_inventory.repository;

import com.jeans_inventory.entity.PartnerStock;
import com.jeans_inventory.entity.PartnerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerStockRepository extends JpaRepository<PartnerStock, Long> {

    Optional<PartnerStock> findByPartnerIdAndStyleId(Long partnerId, Long styleId);

    List<PartnerStock> findAllByPartnerId(Long partnerId);

    List<PartnerStock> findAllByPartnerPartnerType(PartnerType partnerType);
}
