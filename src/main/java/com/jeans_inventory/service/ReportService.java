package com.jeans_inventory.service;

import com.jeans_inventory.dto.InventoryValuation;
import com.jeans_inventory.entity.*;
import com.jeans_inventory.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final StockMovementRepository stockMovementRepository;
    private final PartnerStockRepository partnerStockRepository;
    private final WastageRepository wastageRepository;
    private final AlterRecordRepository alterRecordRepository;
    private final SampleRepository sampleRepository;
    private final NormalStockRepository normalStockRepository;
    private final StyleRepository styleRepository;
    private final StylePricingRepository stylePricingRepository;

    public ReportService(
            StockMovementRepository stockMovementRepository,
            PartnerStockRepository partnerStockRepository,
            WastageRepository wastageRepository,
            AlterRecordRepository alterRecordRepository,
            SampleRepository sampleRepository,
            NormalStockRepository normalStockRepository,
            StyleRepository styleRepository,
            StylePricingRepository stylePricingRepository
    ) {
        this.stockMovementRepository = stockMovementRepository;
        this.partnerStockRepository = partnerStockRepository;
        this.wastageRepository = wastageRepository;
        this.alterRecordRepository = alterRecordRepository;
        this.sampleRepository = sampleRepository;
        this.normalStockRepository = normalStockRepository;
        this.styleRepository = styleRepository;
        this.stylePricingRepository = stylePricingRepository;
    }

    public List<StockMovement> getMovementHistory() {
        return stockMovementRepository.findAll();
    }

    public List<PartnerStock> getPartnerStock() {
        return partnerStockRepository.findAll();
    }

    public List<Wastage> getWastage() {
        return wastageRepository.findAll();
    }

    public List<AlterRecord> getAlters() {
        return alterRecordRepository.findAll();
    }

    public List<Sample> getSamples() {
        return sampleRepository.findAll();
    }

    public List<NormalStock> getNormalStock() {
        return normalStockRepository.findAll();
    }

    public List<InventoryValuation> getInventoryValuation() {
        // sum across normal and partner stock grouped by style
        Map<Long, Long> qtyByStyle = new HashMap<>();

        normalStockRepository.findAll().forEach(s -> {
            Long id = s.getStyle().getId();
            qtyByStyle.put(id, qtyByStyle.getOrDefault(id, 0L) + s.getQuantity());
        });

        partnerStockRepository.findAll().forEach(s -> {
            Long id = s.getStyle().getId();
            qtyByStyle.put(id, qtyByStyle.getOrDefault(id, 0L) + s.getQuantity());
        });

        List<Long> styleIds = new ArrayList<>(qtyByStyle.keySet());

        return styleIds.stream().map(styleId -> {
            Style style = styleRepository.findById(styleId).orElse(null);
            String name = style == null ? "(deleted)" : style.getStyleCode();
            long totalQty = qtyByStyle.getOrDefault(styleId, 0L);
            BigDecimal cost = stylePricingRepository.findByStyleId(styleId)
                    .map(StylePricing::getCostPerPiece)
                    .orElse(BigDecimal.ZERO);
            BigDecimal totalValue = cost.multiply(BigDecimal.valueOf(totalQty));
            return new InventoryValuation(styleId, name, totalQty, cost, totalValue);
        }).collect(Collectors.toList());
    }
}
