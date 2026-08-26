package com.jeans_inventory.controller;

import com.jeans_inventory.entity.ShopAssortment;
import com.jeans_inventory.service.ShopAssortmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop-assortment")
public class ShopAssortmentController {

    private final ShopAssortmentService shopAssortmentService;

    public ShopAssortmentController(ShopAssortmentService shopAssortmentService) {
        this.shopAssortmentService = shopAssortmentService;
    }

    @PostMapping
    public ShopAssortment addAssortment(@RequestBody ShopAssortment assortment) {
        return shopAssortmentService.addAssortment(assortment);
    }

    @GetMapping
    public List<ShopAssortment> getAllAssortment() {
        return shopAssortmentService.getAllAssortment();
    }
}
