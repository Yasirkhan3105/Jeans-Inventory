package com.jeans_inventory.service;

import com.jeans_inventory.entity.*;
import com.jeans_inventory.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final StockMovementRepository stockMovementRepository;
    private final PartnerStockRepository partnerStockRepository;
    private final WastageRepository wastageRepository;
    private final AlterRecordRepository alterRecordRepository;
    private final SampleRepository sampleRepository;

    public ReportService(
            StockMovementRepository stockMovementRepository,
            PartnerStockRepository partnerStockRepository,
            WastageRepository wastageRepository,
            AlterRecordRepository alterRecordRepository,
            SampleRepository sampleRepository
    ) {
        this.stockMovementRepository = stockMovementRepository;
        this.partnerStockRepository = partnerStockRepository;
        this.wastageRepository = wastageRepository;
        this.alterRecordRepository = alterRecordRepository;
        this.sampleRepository = sampleRepository;
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
}
