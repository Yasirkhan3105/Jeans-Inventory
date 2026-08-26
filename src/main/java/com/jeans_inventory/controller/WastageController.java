package com.jeans_inventory.controller;

import com.jeans_inventory.dto.WastageRequest;
import com.jeans_inventory.entity.Wastage;
import com.jeans_inventory.service.WastageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wastage")
public class WastageController {

    private final WastageService wastageService;

    public WastageController(WastageService wastageService) {
        this.wastageService = wastageService;
    }

    @PostMapping
    public Wastage recordWastage(@RequestBody WastageRequest request) {
        return wastageService.recordWastage(request);
    }

    @GetMapping
    public List<Wastage> getAllWastage() {
        return wastageService.getAllWastage();
    }

    @GetMapping("/weaving")
    public List<Wastage> getWeavingWastage() {
        return wastageService.getWeavingWastage();
    }
}
