package com.jeans_inventory.controller;

import com.jeans_inventory.entity.*;
import com.jeans_inventory.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/movements")
    public List<StockMovement> getMovementHistory() {
        return reportService.getMovementHistory();
    }

    @GetMapping("/partner-stock")
    public List<PartnerStock> getPartnerStock() {
        return reportService.getPartnerStock();
    }

    @GetMapping("/wastage")
    public List<Wastage> getWastage() {
        return reportService.getWastage();
    }

    @GetMapping("/alters")
    public List<AlterRecord> getAlters() {
        return reportService.getAlters();
    }

    @GetMapping("/samples")
    public List<Sample> getSamples() {
        return reportService.getSamples();
    }
}
