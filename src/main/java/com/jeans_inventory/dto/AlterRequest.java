package com.jeans_inventory.dto;

import com.jeans_inventory.entity.AlterFaultType;

import java.time.LocalDate;

public class AlterRequest {

    private Long styleId;
    private Integer quantity;
    private AlterFaultType faultType;
    private Long responsiblePartnerId;
    private LocalDate recordedDate;
    private String remarks;

    public Long getStyleId() {
        return styleId;
    }

    public void setStyleId(Long styleId) {
        this.styleId = styleId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public AlterFaultType getFaultType() {
        return faultType;
    }

    public void setFaultType(AlterFaultType faultType) {
        this.faultType = faultType;
    }

    public Long getResponsiblePartnerId() {
        return responsiblePartnerId;
    }

    public void setResponsiblePartnerId(Long responsiblePartnerId) {
        this.responsiblePartnerId = responsiblePartnerId;
    }

    public LocalDate getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(LocalDate recordedDate) {
        this.recordedDate = recordedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
