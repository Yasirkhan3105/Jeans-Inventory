package com.jeans_inventory.dto;

public class StockAdjustRequest {
    private Long styleId;
    private String holderType; // "LOCATION" or "PARTNER"
    private String location; // optional, e.g., "WASHER"
    private Long partnerId; // optional
    private Integer quantity; // positive integer
    private String operation; // "increase" or "decrease"

    public StockAdjustRequest() {}

    public Long getStyleId() { return styleId; }
    public void setStyleId(Long styleId) { this.styleId = styleId; }

    public String getHolderType() { return holderType; }
    public void setHolderType(String holderType) { this.holderType = holderType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Long getPartnerId() { return partnerId; }
    public void setPartnerId(Long partnerId) { this.partnerId = partnerId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
}
