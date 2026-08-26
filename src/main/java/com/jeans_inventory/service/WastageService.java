package com.jeans_inventory.service;

import com.jeans_inventory.dto.WastageRequest;
import com.jeans_inventory.entity.ProductionPartner;
import com.jeans_inventory.entity.StockHolderType;
import com.jeans_inventory.entity.Style;
import com.jeans_inventory.entity.Wastage;
import com.jeans_inventory.entity.WastageStage;
import com.jeans_inventory.repository.ProductionPartnerRepository;
import com.jeans_inventory.repository.StyleRepository;
import com.jeans_inventory.repository.WastageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class WastageService {

    private final StyleRepository styleRepository;
    private final ProductionPartnerRepository productionPartnerRepository;
    private final WastageRepository wastageRepository;
    private final InventoryBalanceService inventoryBalanceService;

    public WastageService(
            StyleRepository styleRepository,
            ProductionPartnerRepository productionPartnerRepository,
            WastageRepository wastageRepository,
            InventoryBalanceService inventoryBalanceService
    ) {
        this.styleRepository = styleRepository;
        this.productionPartnerRepository = productionPartnerRepository;
        this.wastageRepository = wastageRepository;
        this.inventoryBalanceService = inventoryBalanceService;
    }

    @Transactional
    public Wastage recordWastage(WastageRequest request) {
        validateRequest(request);

        Style style = styleRepository.findById(request.getStyleId())
                .orElseThrow(() -> new IllegalArgumentException("Style was not found."));
        ProductionPartner sourcePartner = inventoryBalanceService.getPartnerForHolder(
                request.getSourceHolderType(),
                request.getSourceLocation(),
                request.getSourcePartnerId()
        );
        ProductionPartner responsiblePartner = findResponsiblePartner(request.getResponsiblePartnerId());

        inventoryBalanceService.decrease(
                style,
                request.getSourceHolderType(),
                request.getSourceLocation(),
                request.getSourcePartnerId(),
                request.getQuantity()
        );

        Wastage wastage = new Wastage();
        wastage.setStyle(style);
        wastage.setStage(request.getStage());
        wastage.setSourceHolderType(request.getSourceHolderType());
        wastage.setSourceLocation(request.getSourceLocation());
        wastage.setSourcePartner(sourcePartner);
        wastage.setResponsiblePartner(responsiblePartner);
        wastage.setQuantity(request.getQuantity());
        wastage.setWastageDate(
                request.getWastageDate() == null ? LocalDate.now() : request.getWastageDate()
        );
        wastage.setReason(request.getReason().trim());

        return wastageRepository.save(wastage);
    }

    public List<Wastage> getAllWastage() {
        return wastageRepository.findAll();
    }

    public List<Wastage> getWeavingWastage() {
        return wastageRepository.findAllByStage(WastageStage.WEAVING);
    }

    private ProductionPartner findResponsiblePartner(Long partnerId) {
        if (partnerId == null) {
            return null;
        }

        return productionPartnerRepository.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Responsible partner was not found."));
    }

    private void validateRequest(WastageRequest request) {
        if (request == null
                || request.getStyleId() == null
                || request.getStage() == null
                || request.getSourceHolderType() == null
                || request.getQuantity() == null
                || request.getQuantity() <= 0
                || request.getReason() == null
                || request.getReason().isBlank()) {
            throw new IllegalArgumentException(
                    "Style, stage, source, positive quantity, and reason are required for wastage."
            );
        }
    }
}
