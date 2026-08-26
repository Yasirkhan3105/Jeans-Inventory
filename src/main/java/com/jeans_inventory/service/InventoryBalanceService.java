package com.jeans_inventory.service;

import com.jeans_inventory.entity.*;
import com.jeans_inventory.repository.NormalStockRepository;
import com.jeans_inventory.repository.PartnerStockRepository;
import com.jeans_inventory.repository.ProductionPartnerRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryBalanceService {

    private final NormalStockRepository normalStockRepository;
    private final ProductionPartnerRepository productionPartnerRepository;
    private final PartnerStockRepository partnerStockRepository;

    public InventoryBalanceService(
            NormalStockRepository normalStockRepository,
            ProductionPartnerRepository productionPartnerRepository,
            PartnerStockRepository partnerStockRepository
    ) {
        this.normalStockRepository = normalStockRepository;
        this.productionPartnerRepository = productionPartnerRepository;
        this.partnerStockRepository = partnerStockRepository;
    }

    public void increase(
            Style style,
            StockHolderType holderType,
            Location location,
            Long partnerId,
            Integer quantity
    ) {
        validateQuantity(quantity);

        if (holderType == StockHolderType.LOCATION) {
            increaseLocationStock(style, location, partnerId, quantity);
            return;
        }

        ProductionPartner partner = resolvePartner(holderType, location, partnerId);
        PartnerStock stock = partnerStockRepository
                .findByPartnerIdAndStyleId(partner.getId(), style.getId())
                .orElseGet(() -> newPartnerStock(partner, style));

        stock.setQuantity(stock.getQuantity() + quantity);
        partnerStockRepository.save(stock);
    }

    public void decrease(
            Style style,
            StockHolderType holderType,
            Location location,
            Long partnerId,
            Integer quantity
    ) {
        validateQuantity(quantity);

        if (holderType == StockHolderType.LOCATION) {
            decreaseLocationStock(style, location, partnerId, quantity);
            return;
        }

        ProductionPartner partner = resolvePartner(holderType, location, partnerId);
        PartnerStock stock = partnerStockRepository
                .findByPartnerIdAndStyleId(partner.getId(), style.getId())
                .orElseThrow(() -> new IllegalArgumentException("No stock exists at the selected partner."));

        if (stock.getQuantity() < quantity) {
            throw new IllegalArgumentException("The selected partner does not have enough stock.");
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        partnerStockRepository.save(stock);
    }

    public ProductionPartner getPartnerForHolder(
            StockHolderType holderType,
            Location location,
            Long partnerId
    ) {
        if (holderType == StockHolderType.LOCATION) {
            validateLocationHolder(location, partnerId);
            return null;
        }

        return resolvePartner(holderType, location, partnerId);
    }

    private void increaseLocationStock(Style style, Location location, Long partnerId, Integer quantity) {
        validateLocationHolder(location, partnerId);

        NormalStock stock = normalStockRepository
                .findByStyle_IdAndLocation(style.getId(), location)
                .orElseGet(() -> newNormalStock(style, location));

        stock.setQuantity(stock.getQuantity() + quantity);
        normalStockRepository.save(stock);
    }

    private void decreaseLocationStock(Style style, Location location, Long partnerId, Integer quantity) {
        validateLocationHolder(location, partnerId);

        NormalStock stock = normalStockRepository
                .findByStyle_IdAndLocation(style.getId(), location)
                .orElseThrow(() -> new IllegalArgumentException("No stock exists at the selected location."));

        if (stock.getQuantity() < quantity) {
            throw new IllegalArgumentException("The selected location does not have enough stock.");
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        normalStockRepository.save(stock);
    }

    private ProductionPartner resolvePartner(
            StockHolderType holderType,
            Location location,
            Long partnerId
    ) {
        if (location != null || partnerId == null) {
            throw new IllegalArgumentException("A partner holder requires a partner ID and no location.");
        }

        ProductionPartner partner = productionPartnerRepository.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Selected partner was not found."));

        PartnerType expectedType = holderType == StockHolderType.FABRICATOR
                ? PartnerType.FABRICATOR
                : PartnerType.WASHER;

        if (partner.getPartnerType() != expectedType) {
            throw new IllegalArgumentException("Selected partner does not match the movement holder type.");
        }

        if (!partner.isActive()) {
            throw new IllegalArgumentException("Selected partner is inactive.");
        }

        return partner;
    }

    private void validateLocationHolder(Location location, Long partnerId) {
        if (location == null || partnerId != null) {
            throw new IllegalArgumentException("A location holder requires a location and no partner ID.");
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }

    private NormalStock newNormalStock(Style style, Location location) {
        NormalStock stock = new NormalStock();
        stock.setStyle(style);
        stock.setLocation(location);
        stock.setQuantity(0);
        return stock;
    }

    private PartnerStock newPartnerStock(ProductionPartner partner, Style style) {
        PartnerStock stock = new PartnerStock();
        stock.setPartner(partner);
        stock.setStyle(style);
        stock.setQuantity(0);
        return stock;
    }
}
