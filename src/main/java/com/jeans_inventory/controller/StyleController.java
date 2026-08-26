package com.jeans_inventory.controller;

import com.jeans_inventory.entity.Style;
import com.jeans_inventory.service.Styleservice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/styles")
public class StyleController {

    private final Styleservice styleService;

    public StyleController(Styleservice styleService) {
        this.styleService = styleService;
    }

    @PostMapping
    public Style createStyle(@RequestBody Style style) {
        return styleService.createStyle(style);
    }

    @GetMapping
    public List<Style> getAllStyles() {
        return styleService.getAllStyles();
    }
}