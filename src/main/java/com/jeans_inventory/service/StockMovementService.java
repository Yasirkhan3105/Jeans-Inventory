package com.jeans_inventory.service;

import com.jeans_inventory.dto.StockMovementRequest;
import com.jeans_inventory.entity.ProductionPartner;
import com.jeans_inventory.entity.StockHolderType;
import com.jeans_inventory.entity.StockMovement;
import com.jeans_inventory.entity.Style;
import com.jeans_inventory.repository.StockMovementRepository;
import com.jeans_inventory.repository.StyleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockMovementService {

    private final StyleRepository styleRepository;
    private final StockMovementRepository stockMovementRepository;
    private final InventoryBalanceService inventoryBalanceService;

    public StockMovementService(
            StyleRepository styleRepository,
            StockMovementRepository stockMovementRepository,
            InventoryBalanceService inventoryBalanceService
    ) {
        this.styleRepository = styleRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.inventoryBalanceService = inventoryBalanceService;
    }

    @Transactional
    public StockMovement moveStock(StockMovementRequest request) {
        validateRequest(request);

        Style style = styleRepository.findById(request.getStyleId())
                .orElseThrow(() -> new IllegalArgumentException("Style was not found."));

        if (isSameHolder(request)) {
            throw new IllegalArgumentException("Source and destination cannot be the same.");
        }

        ProductionPartner fromPartner = inventoryBalanceService.getPartnerForHolder(
                request.getFromHolderType(),
                request.getFromLocation(),
                request.getFromPartnerId()
        );
        ProductionPartner toPartner = inventoryBalanceService.getPartnerForHolder(
                request.getToHolderType(),
                request.getToLocation(),
                request.getToPartnerId()
        );

        inventoryBalanceService.decrease(
                style,
                request.getFromHolderType(),
                request.getFromLocation(),
                request.getFromPartnerId(),
                request.getQuantity()
        );
        inventoryBalanceService.increase(
                style,
                request.getToHolderType(),
                request.getToLocation(),
                request.getToPartnerId(),
                request.getQuantity()
        );

        StockMovement movement = new StockMovement();
        movement.setStyle(style);
        movement.setFromHolderType(request.getFromHolderType());
        movement.setFromLocation(request.getFromLocation());
        movement.setFromPartner(fromPartner);
        movement.setToHolderType(request.getToHolderType());
        movement.setToLocation(request.getToLocation());
        movement.setToPartner(toPartner);
        movement.setQuantity(request.getQuantity());
        movement.setMovedAt(LocalDateTime.now());
        movement.setRemarks(request.getRemarks());

        return stockMovementRepository.save(movement);
    }

    public List<StockMovement> getAllMovements() {
        return stockMovementRepository.findAll();
    }

    private void validateRequest(StockMovementRequest request) {
        if (request == null
                || request.getStyleId() == null
                || request.getFromHolderType() == null
                || request.getToHolderType() == null
                || request.getQuantity() == null
                || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Style, source, destination, and a positive quantity are required.");
        }
    }

    private boolean isSameHolder(StockMovementRequest request) {
        if (request.getFromHolderType() != request.getToHolderType()) {
            return false;
        }

        if (request.getFromHolderType() == StockHolderType.LOCATION) {
            return request.getFromLocation() == request.getToLocation();
        }

        return request.getFromPartnerId() != null
                && request.getFromPartnerId().equals(request.getToPartnerId());
    }
}
