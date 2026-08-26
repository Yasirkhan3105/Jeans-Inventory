package com.jeans_inventory.service;

import com.jeans_inventory.entity.NormalStock;
import com.jeans_inventory.entity.Style;
import com.jeans_inventory.repository.NormalStockRepository;
import com.jeans_inventory.repository.StyleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NormalStockService {

    private final NormalStockRepository normalStockRepository;
    private final StyleRepository styleRepository;

    public NormalStockService(
            NormalStockRepository normalStockRepository,
            StyleRepository styleRepository
    ) {
        this.normalStockRepository = normalStockRepository;
        this.styleRepository = styleRepository;
    }

    public NormalStock addStock(NormalStock stock) {

        // Find the Style from the styleId received from Postman
        Style style = styleRepository.findById(stock.getStyleId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Style not found with id: " + stock.getStyleId()
                        )
                );

        // Connect the Style object to the NormalStock
        stock.setStyle(style);

        // Check whether this style already has stock at this location
        NormalStock existing = normalStockRepository
                .findByStyle_IdAndLocation(
                        stock.getStyle().getId(),
                        stock.getLocation()
                )
                .orElse(null);

        // If stock already exists, increase its quantity
        if (existing != null) {

            existing.setQuantity(
                    existing.getQuantity() + stock.getQuantity()
            );

            return normalStockRepository.save(existing);
        }

        // Otherwise create a new stock record
        return normalStockRepository.save(stock);
    }

    public List<NormalStock> getAllStock() {
        return normalStockRepository.findAll();
    }
}