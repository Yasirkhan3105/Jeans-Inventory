package com.jeans_inventory.service;

import com.jeans_inventory.entity.PartnerStock;
import com.jeans_inventory.entity.ProductionPartner;
import com.jeans_inventory.repository.PartnerStockRepository;
import com.jeans_inventory.repository.ProductionPartnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionPartnerService {

    private final ProductionPartnerRepository productionPartnerRepository;
    private final PartnerStockRepository partnerStockRepository;

    public ProductionPartnerService(
            ProductionPartnerRepository productionPartnerRepository,
            PartnerStockRepository partnerStockRepository
    ) {
        this.productionPartnerRepository = productionPartnerRepository;
        this.partnerStockRepository = partnerStockRepository;
    }

    public ProductionPartner createPartner(ProductionPartner partner) {
        if (partner.getName() == null || partner.getName().isBlank()) {
            throw new IllegalArgumentException("Partner name is required.");
        }

        partner.setName(partner.getName().trim());
        return productionPartnerRepository.save(partner);
    }

    public List<ProductionPartner> getAllPartners() {
        return productionPartnerRepository.findAll();
    }

    public List<PartnerStock> getPartnerStock(Long partnerId) {
        return partnerStockRepository.findAllByPartnerId(partnerId);
    }
}
