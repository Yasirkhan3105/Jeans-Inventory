package com.jeans_inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_holder_type", nullable = false)
    private StockHolderType fromHolderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_location")
    private Location fromLocation;

    @ManyToOne
    @JoinColumn(name = "from_partner_id")
    private ProductionPartner fromPartner;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_holder_type", nullable = false)
    private StockHolderType toHolderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_location")
    private Location toLocation;

    @ManyToOne
    @JoinColumn(name = "to_partner_id")
    private ProductionPartner toPartner;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "moved_at", nullable = false)
    private LocalDateTime movedAt;

    @Column(length = 1000)
    private String remarks;

    public StockMovement() {
    }

    public Long getId() {
        return id;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
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

    public ProductionPartner getFromPartner() {
        return fromPartner;
    }

    public void setFromPartner(ProductionPartner fromPartner) {
        this.fromPartner = fromPartner;
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

    public ProductionPartner getToPartner() {
        return toPartner;
    }

    public void setToPartner(ProductionPartner toPartner) {
        this.toPartner = toPartner;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getMovedAt() {
        return movedAt;
    }

    public void setMovedAt(LocalDateTime movedAt) {
        this.movedAt = movedAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
