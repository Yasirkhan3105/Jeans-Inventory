package com.jeans_inventory.dto;

import com.jeans_inventory.entity.Location;
import com.jeans_inventory.entity.StockHolderType;

public class StockMovementRequest {

    private Long styleId;
    private StockHolderType fromHolderType;
    private Location fromLocation;
    private Long fromPartnerId;
    private StockHolderType toHolderType;
    private Location toLocation;
    private Long toPartnerId;
    private Integer quantity;
    private String remarks;

    public Long getStyleId() {
        return styleId;
    }

    public void setStyleId(Long styleId) {
        this.styleId = styleId;
    }

    public StockHolderType getFromHolderType() {
        return fromHolderType;
    }

    public void setFromHolderType(StockHolderType fromHolderType) {
        this.fromHolderType = fromHolderType;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(Location fromLocation) {
        this.fromLocation = fromLocation;
    }

    public Long getFromPartnerId() {
        return fromPartnerId;
    }

    public void setFromPartnerId(Long fromPartnerId) {
        this.fromPartnerId = fromPartnerId;
    }

    public StockHolderType getToHolderType() {
        return toHolderType;
    }

    public void setToHolderType(StockHolderType toHolderType) {
        this.toHolderType = toHolderType;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public void setToLocation(Location toLocation) {
        this.toLocation = toLocation;
    }

    public Long getToPartnerId() {
        return toPartnerId;
    }

    public void setToPartnerId(Long toPartnerId) {
        this.toPartnerId = toPartnerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
