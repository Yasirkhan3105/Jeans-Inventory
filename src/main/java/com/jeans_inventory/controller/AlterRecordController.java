package com.jeans_inventory.controller;

import com.jeans_inventory.dto.AlterRequest;
import com.jeans_inventory.entity.AlterRecord;
import com.jeans_inventory.service.AlterRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alters")
public class AlterRecordController {

    private final AlterRecordService alterRecordService;

    public AlterRecordController(AlterRecordService alterRecordService) {
        this.alterRecordService = alterRecordService;
    }

    @PostMapping
    public AlterRecord recordAlter(@RequestBody AlterRequest request) {
        return alterRecordService.recordAlter(request);
    }

    @GetMapping
    public List<AlterRecord> getAllAlters() {
        return alterRecordService.getAllAlters();
    }
}
