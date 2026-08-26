package com.jeans_inventory.controller;

import com.jeans_inventory.entity.Sample;
import com.jeans_inventory.service.SampleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/samples")
public class SampleController {

    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @PostMapping
    public Sample addSample(@RequestBody Sample sample) {
        return sampleService.addSample(sample);
    }

    @GetMapping
    public List<Sample> getAllSamples() {
        return sampleService.getAllSamples();
    }
}
