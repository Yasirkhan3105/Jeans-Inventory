package com.jeans_inventory.service;

import com.jeans_inventory.entity.Sample;
import com.jeans_inventory.repository.SampleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public Sample addSample(Sample sample) {
        validateQuantity(sample.getQuantity());

        Sample existing = sampleRepository
                .findByStyleIdAndLocation(sample.getStyle().getId(), sample.getLocation())
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + sample.getQuantity());
            return sampleRepository.save(existing);
        }

        return sampleRepository.save(sample);
    }

    public List<Sample> getAllSamples() {
        return sampleRepository.findAll();
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }
}
