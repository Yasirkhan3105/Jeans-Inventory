package com.jeans_inventory.controller;

import com.jeans_inventory.dto.StockAdjustRequest;
import com.jeans_inventory.entity.NormalStock;
import com.jeans_inventory.entity.Style;
import com.jeans_inventory.entity.Location;
import com.jeans_inventory.entity.StockHolderType;
import com.jeans_inventory.repository.StyleRepository;
import com.jeans_inventory.service.InventoryBalanceService;
import com.jeans_inventory.service.NormalStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class NormalStockController {

    private final NormalStockService normalStockService;
    private final InventoryBalanceService inventoryBalanceService;
    private final StyleRepository styleRepository;

    public NormalStockController(NormalStockService normalStockService,
                                 InventoryBalanceService inventoryBalanceService,
                                 StyleRepository styleRepository) {
        this.normalStockService = normalStockService;
        this.inventoryBalanceService = inventoryBalanceService;
        this.styleRepository = styleRepository;
    }

    @PostMapping
    public NormalStock addStock(@RequestBody NormalStock stock) {
        return normalStockService.addStock(stock);
    }

    @GetMapping
    public List<NormalStock> getAllStock() {
        return normalStockService.getAllStock();
    }

    @PostMapping("/adjust")
    public void adjustStock(@RequestBody StockAdjustRequest req) {
        Style style = styleRepository.findById(req.getStyleId())
                .orElseThrow(() -> new IllegalArgumentException("Style not found: " + req.getStyleId()));

        StockHolderType holderType = StockHolderType.valueOf(req.getHolderType());
        Location location = req.getLocation() == null ? null : Location.valueOf(req.getLocation());
        Long partnerId = req.getPartnerId();
        Integer qty = req.getQuantity();

        if ("increase".equalsIgnoreCase(req.getOperation())) {
            inventoryBalanceService.increase(style, holderType, location, partnerId, qty);
            return;
        }

        if ("decrease".equalsIgnoreCase(req.getOperation())) {
            inventoryBalanceService.decrease(style, holderType, location, partnerId, qty);
            return;
        }

        throw new IllegalArgumentException("Unknown operation: " + req.getOperation());
    }
}
