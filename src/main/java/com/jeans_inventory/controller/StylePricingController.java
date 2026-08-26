package com.jeans_inventory.controller;

import com.jeans_inventory.entity.StylePricing;
import com.jeans_inventory.service.StylePricingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing")
public class StylePricingController {

    private final StylePricingService stylePricingService;

    public StylePricingController(StylePricingService stylePricingService) {
        this.stylePricingService = stylePricingService;
    }

    @PostMapping
    public StylePricing savePricing(@RequestBody StylePricing pricing) {
        return stylePricingService.savePricing(pricing);
    }

    @GetMapping
    public List<StylePricing> getAllPricing() {
        return stylePricingService.getAllPricing();
    }
}
