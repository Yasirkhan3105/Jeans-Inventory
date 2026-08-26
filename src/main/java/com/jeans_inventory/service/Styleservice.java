package com.jeans_inventory.service;

import com.jeans_inventory.entity.Style;
import com.jeans_inventory.repository.StyleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Styleservice {

    private final StyleRepository styleRepository;

    public Styleservice(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    public Style createStyle(Style style) {
        return styleRepository.save(style);
    }

    public List<Style> getAllStyles() {
        return styleRepository.findAll();
    }
}