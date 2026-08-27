package com.jeans_inventory.dto;

import java.math.BigDecimal;

public class InventoryValuation {
    private Long styleId;
    private String styleName;
    private long totalQuantity;
    private BigDecimal costPerPiece;
    private BigDecimal totalValue;

    public InventoryValuation() {}

    public InventoryValuation(Long styleId, String styleName, long totalQuantity, BigDecimal costPerPiece, BigDecimal totalValue) {
        this.styleId = styleId;
        this.styleName = styleName;
        this.totalQuantity = totalQuantity;
        this.costPerPiece = costPerPiece;
        this.totalValue = totalValue;
    }

    public Long getStyleId() { return styleId; }
    public String getStyleName() { return styleName; }
    public long getTotalQuantity() { return totalQuantity; }
    public BigDecimal getCostPerPiece() { return costPerPiece; }
    public BigDecimal getTotalValue() { return totalValue; }

    public void setStyleId(Long styleId) { this.styleId = styleId; }
    public void setStyleName(String styleName) { this.styleName = styleName; }
    public void setTotalQuantity(long totalQuantity) { this.totalQuantity = totalQuantity; }
    public void setCostPerPiece(BigDecimal costPerPiece) { this.costPerPiece = costPerPiece; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }
}
