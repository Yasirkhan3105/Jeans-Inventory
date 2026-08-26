package com.jeans_inventory.controller;

import com.jeans_inventory.entity.PartnerStock;
import com.jeans_inventory.entity.ProductionPartner;
import com.jeans_inventory.service.ProductionPartnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
public class ProductionPartnerController {

    private final ProductionPartnerService productionPartnerService;

    public ProductionPartnerController(ProductionPartnerService productionPartnerService) {
        this.productionPartnerService = productionPartnerService;
    }

    @PostMapping
    public ProductionPartner createPartner(@RequestBody ProductionPartner partner) {
        return productionPartnerService.createPartner(partner);
    }

    @GetMapping
    public List<ProductionPartner> getAllPartners() {
        return productionPartnerService.getAllPartners();
    }

    @GetMapping("/{partnerId}/stock")
    public List<PartnerStock> getPartnerStock(@PathVariable Long partnerId) {
        return productionPartnerService.getPartnerStock(partnerId);
    }
}
