package com.jeans_inventory.service;

import com.jeans_inventory.entity.ShopAssortment;
import com.jeans_inventory.repository.ShopAssortmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopAssortmentService {

    private final ShopAssortmentRepository shopAssortmentRepository;

    public ShopAssortmentService(ShopAssortmentRepository shopAssortmentRepository) {
        this.shopAssortmentRepository = shopAssortmentRepository;
    }

    public ShopAssortment addAssortment(ShopAssortment assortment) {
        validateSize(assortment.getSize());

        ShopAssortment existing = shopAssortmentRepository
                .findByStyleIdAndSize(
                        assortment.getStyle().getId(),
                        assortment.getSize()
                )
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(
                    existing.getQuantity() + assortment.getQuantity()
            );

            return shopAssortmentRepository.save(existing);
        }

        return shopAssortmentRepository.save(assortment);
    }

    public List<ShopAssortment> getAllAssortment() {
        return shopAssortmentRepository.findAll();
    }

    private void validateSize(Integer size) {
        if (size == null || (size != 30 && size != 32 && size != 34 && size != 36)) {
            throw new IllegalArgumentException(
                    "Shop assortment size must be 30, 32, 34, or 36."
            );
        }
    }
}
