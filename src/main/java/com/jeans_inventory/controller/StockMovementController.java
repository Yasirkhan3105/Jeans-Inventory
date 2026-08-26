package com.jeans_inventory.controller;

import com.jeans_inventory.dto.StockMovementRequest;
import com.jeans_inventory.entity.StockMovement;
import com.jeans_inventory.service.StockMovementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    public StockMovement moveStock(@RequestBody StockMovementRequest request) {
        return stockMovementService.moveStock(request);
    }

    @GetMapping
    public List<StockMovement> getAllMovements() {
        return stockMovementService.getAllMovements();
    }
}
