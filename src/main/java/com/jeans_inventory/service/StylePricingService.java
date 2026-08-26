package com.jeans_inventory.service;

import com.jeans_inventory.entity.StylePricing;
import com.jeans_inventory.repository.StylePricingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StylePricingService {

    private final StylePricingRepository stylePricingRepository;

    public StylePricingService(StylePricingRepository stylePricingRepository) {
        this.stylePricingRepository = stylePricingRepository;
    }

    public StylePricing savePricing(StylePricing pricing) {
        validatePricing(pricing);

        StylePricing existing = stylePricingRepository
                .findByStyleId(pricing.getStyle().getId())
                .orElse(null);

        if (existing != null) {
            existing.setCostPerPiece(pricing.getCostPerPiece());
            existing.setSellingPricePerPiece(pricing.getSellingPricePerPiece());
            return stylePricingRepository.save(existing);
        }

        return stylePricingRepository.save(pricing);
    }

    public List<StylePricing> getAllPricing() {
        return stylePricingRepository.findAll();
    }

    private void validatePricing(StylePricing pricing) {
        if (pricing == null
                || pricing.getStyle() == null
                || pricing.getStyle().getId() == null
                || isNegative(pricing.getCostPerPiece())
                || isNegative(pricing.getSellingPricePerPiece())) {
            throw new IllegalArgumentException(
                    "Style, non-negative cost per piece, and non-negative selling price per piece are required."
            );
        }
    }

    private boolean isNegative(BigDecimal value) {
        return value == null || value.signum() < 0;
    }
}
