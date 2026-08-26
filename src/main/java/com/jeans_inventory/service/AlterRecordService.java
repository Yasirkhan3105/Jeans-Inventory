package com.jeans_inventory.service;

import com.jeans_inventory.dto.AlterRequest;
import com.jeans_inventory.entity.AlterFaultType;
import com.jeans_inventory.entity.AlterRecord;
import com.jeans_inventory.entity.PartnerType;
import com.jeans_inventory.entity.ProductionPartner;
import com.jeans_inventory.entity.Style;
import com.jeans_inventory.repository.AlterRecordRepository;
import com.jeans_inventory.repository.ProductionPartnerRepository;
import com.jeans_inventory.repository.StyleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlterRecordService {

    private final StyleRepository styleRepository;
    private final ProductionPartnerRepository productionPartnerRepository;
    private final AlterRecordRepository alterRecordRepository;

    public AlterRecordService(
            StyleRepository styleRepository,
            ProductionPartnerRepository productionPartnerRepository,
            AlterRecordRepository alterRecordRepository
    ) {
        this.styleRepository = styleRepository;
        this.productionPartnerRepository = productionPartnerRepository;
        this.alterRecordRepository = alterRecordRepository;
    }

    public AlterRecord recordAlter(AlterRequest request) {
        validateRequest(request);

        Style style = styleRepository.findById(request.getStyleId())
                .orElseThrow(() -> new IllegalArgumentException("Style was not found."));
        ProductionPartner responsiblePartner = productionPartnerRepository
                .findById(request.getResponsiblePartnerId())
                .orElseThrow(() -> new IllegalArgumentException("Responsible partner was not found."));

        PartnerType expectedPartnerType = request.getFaultType() == AlterFaultType.FABRICATOR
                ? PartnerType.FABRICATOR
                : PartnerType.WASHER;

        if (responsiblePartner.getPartnerType() != expectedPartnerType) {
            throw new IllegalArgumentException("Responsible partner does not match the selected fault type.");
        }

        AlterRecord alterRecord = new AlterRecord();
        alterRecord.setStyle(style);
        alterRecord.setQuantity(request.getQuantity());
        alterRecord.setFaultType(request.getFaultType());
        alterRecord.setResponsiblePartner(responsiblePartner);
        alterRecord.setRecordedDate(
                request.getRecordedDate() == null ? LocalDate.now() : request.getRecordedDate()
        );
        alterRecord.setRemarks(request.getRemarks());

        return alterRecordRepository.save(alterRecord);
    }

    public List<AlterRecord> getAllAlters() {
        return alterRecordRepository.findAll();
    }

    private void validateRequest(AlterRequest request) {
        if (request == null
                || request.getStyleId() == null
                || request.getQuantity() == null
                || request.getQuantity() <= 0
                || request.getFaultType() == null
                || request.getResponsiblePartnerId() == null) {
            throw new IllegalArgumentException(
                    "Style, positive quantity, fault type, and responsible partner are required for alters."
            );
        }
    }
}
