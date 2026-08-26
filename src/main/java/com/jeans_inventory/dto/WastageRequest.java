package com.jeans_inventory.dto;

import com.jeans_inventory.entity.Location;
import com.jeans_inventory.entity.StockHolderType;
import com.jeans_inventory.entity.WastageStage;

import java.time.LocalDate;

public class WastageRequest {

    private Long styleId;
    private WastageStage stage;
    private StockHolderType sourceHolderType;
    private Location sourceLocation;
    private Long sourcePartnerId;
    private Long responsiblePartnerId;
    private Integer quantity;
    private LocalDate wastageDate;
    private String reason;

    public Long getStyleId() {
        return styleId;
    }

    public void setStyleId(Long styleId) {
        this.styleId = styleId;
    }

    public WastageStage getStage() {
        return stage;
    }

    public void setStage(WastageStage stage) {
        this.stage = stage;
    }

    public StockHolderType getSourceHolderType() {
        return sourceHolderType;
    }

    public void setSourceHolderType(StockHolderType sourceHolderType) {
        this.sourceHolderType = sourceHolderType;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(Location sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public Long getSourcePartnerId() {
        return sourcePartnerId;
    }

    public void setSourcePartnerId(Long sourcePartnerId) {
        this.sourcePartnerId = sourcePartnerId;
    }

    public Long getResponsiblePartnerId() {
        return responsiblePartnerId;
    }

    public void setResponsiblePartnerId(Long responsiblePartnerId) {
        this.responsiblePartnerId = responsiblePartnerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getWastageDate() {
        return wastageDate;
    }

    public void setWastageDate(LocalDate wastageDate) {
        this.wastageDate = wastageDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
