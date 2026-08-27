package com.jeans_inventory.service;

import com.jeans_inventory.dto.DashboardSummary;
import com.jeans_inventory.entity.Location;
import com.jeans_inventory.entity.PartnerStock;
import com.jeans_inventory.entity.PartnerType;
import com.jeans_inventory.entity.NormalStock;
import com.jeans_inventory.entity.StylePricing;
import com.jeans_inventory.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardService {

    private final StyleRepository styleRepository;
    private final NormalStockRepository normalStockRepository;
    private final PartnerStockRepository partnerStockRepository;
    private final ShopAssortmentRepository shopAssortmentRepository;
    private final SampleRepository sampleRepository;
    private final WastageRepository wastageRepository;
    private final AlterRecordRepository alterRecordRepository;
    private final StylePricingRepository stylePricingRepository;

    public DashboardService(
            StyleRepository styleRepository,
            NormalStockRepository normalStockRepository,
            PartnerStockRepository partnerStockRepository,
            ShopAssortmentRepository shopAssortmentRepository,
            SampleRepository sampleRepository,
            WastageRepository wastageRepository,
            AlterRecordRepository alterRecordRepository,
            StylePricingRepository stylePricingRepository
    ) {
        this.styleRepository = styleRepository;
        this.normalStockRepository = normalStockRepository;
        this.partnerStockRepository = partnerStockRepository;
        this.shopAssortmentRepository = shopAssortmentRepository;
        this.sampleRepository = sampleRepository;
        this.wastageRepository = wastageRepository;
        this.alterRecordRepository = alterRecordRepository;
        this.stylePricingRepository = stylePricingRepository;
    }

    public DashboardSummary getSummary() {
        BigDecimal normalStockValue = calculateNormalStockValue();
        BigDecimal fabricatorStockValue = calculatePartnerStockValue(PartnerType.FABRICATOR);
        BigDecimal washerStockValue = calculatePartnerStockValue(PartnerType.WASHER);
        BigDecimal totalInventoryValue = normalStockValue.add(fabricatorStockValue).add(washerStockValue);

        return new DashboardSummary(
                styleRepository.count(),
                normalStockQuantity(Location.SHOP),
                normalStockQuantity(Location.FINISHING),
                partnerStockQuantity(PartnerType.FABRICATOR),
                partnerStockQuantity(PartnerType.WASHER),
                shopAssortmentRepository.findAll().stream().mapToLong(stock -> stock.getQuantity()).sum(),
                sampleRepository.findAll().stream().mapToLong(sample -> sample.getQuantity()).sum(),
                wastageRepository.findAll().stream().mapToLong(wastage -> wastage.getQuantity()).sum(),
                alterRecordRepository.findAll().stream().mapToLong(alters -> alters.getQuantity()).sum(),
                normalStockValue,
                fabricatorStockValue,
                washerStockValue,
                totalInventoryValue
        );
    }

    private long normalStockQuantity(Location location) {
        return normalStockRepository.findAllByLocation(location).stream()
                .mapToLong(stock -> stock.getQuantity())
                .sum();
    }

    private long partnerStockQuantity(PartnerType partnerType) {
        return partnerStockRepository.findAllByPartnerPartnerType(partnerType).stream()
                .mapToLong(PartnerStock::getQuantity)
                .sum();
    }

    private BigDecimal calculateNormalStockValue() {
        return normalStockRepository.findAll().stream()
                .map(stock -> {
                    Long styleId = stock.getStyle().getId();
                    return stylePricingRepository.findByStyleId(styleId)
                            .map(StylePricing::getCostPerPiece)
                            .orElse(BigDecimal.ZERO)
                            .multiply(BigDecimal.valueOf(stock.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePartnerStockValue(PartnerType partnerType) {
        return partnerStockRepository.findAllByPartnerPartnerType(partnerType).stream()
                .map(stock -> {
                    Long styleId = stock.getStyle().getId();
                    return stylePricingRepository.findByStyleId(styleId)
                            .map(StylePricing::getCostPerPiece)
                            .orElse(BigDecimal.ZERO)
                            .multiply(BigDecimal.valueOf(stock.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
