package com.jeans_inventory.controller;

import com.jeans_inventory.entity.NormalStock;
import com.jeans_inventory.service.NormalStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class NormalStockController {

    private final NormalStockService normalStockService;

    public NormalStockController(NormalStockService normalStockService) {
        this.normalStockService = normalStockService;
    }

    @PostMapping
    public NormalStock addStock(@RequestBody NormalStock stock) {
        return normalStockService.addStock(stock);
    }

    @GetMapping
    public List<NormalStock> getAllStock() {
        return normalStockService.getAllStock();
    }
}
