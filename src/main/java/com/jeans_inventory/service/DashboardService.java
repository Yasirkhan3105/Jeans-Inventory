package com.jeans_inventory.service;

import com.jeans_inventory.dto.DashboardSummary;
import com.jeans_inventory.entity.Location;
import com.jeans_inventory.entity.PartnerStock;
import com.jeans_inventory.entity.PartnerType;
import com.jeans_inventory.repository.*;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final StyleRepository styleRepository;
    private final NormalStockRepository normalStockRepository;
    private final PartnerStockRepository partnerStockRepository;
    private final ShopAssortmentRepository shopAssortmentRepository;
    private final SampleRepository sampleRepository;
    private final WastageRepository wastageRepository;
    private final AlterRecordRepository alterRecordRepository;

    public DashboardService(
            StyleRepository styleRepository,
            NormalStockRepository normalStockRepository,
            PartnerStockRepository partnerStockRepository,
            ShopAssortmentRepository shopAssortmentRepository,
            SampleRepository sampleRepository,
            WastageRepository wastageRepository,
            AlterRecordRepository alterRecordRepository
    ) {
        this.styleRepository = styleRepository;
        this.normalStockRepository = normalStockRepository;
        this.partnerStockRepository = partnerStockRepository;
        this.shopAssortmentRepository = shopAssortmentRepository;
        this.sampleRepository = sampleRepository;
        this.wastageRepository = wastageRepository;
        this.alterRecordRepository = alterRecordRepository;
    }

    public DashboardSummary getSummary() {
        return new DashboardSummary(
                styleRepository.count(),
                normalStockQuantity(Location.SHOP),
                normalStockQuantity(Location.FINISHING),
                partnerStockQuantity(PartnerType.FABRICATOR),
                partnerStockQuantity(PartnerType.WASHER),
                shopAssortmentRepository.findAll().stream().mapToLong(stock -> stock.getQuantity()).sum(),
                sampleRepository.findAll().stream().mapToLong(sample -> sample.getQuantity()).sum(),
                wastageRepository.findAll().stream().mapToLong(wastage -> wastage.getQuantity()).sum(),
                alterRecordRepository.findAll().stream().mapToLong(alters -> alters.getQuantity()).sum()
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
}
