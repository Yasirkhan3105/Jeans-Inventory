package com.jeans_inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "wastage_records")
public class Wastage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WastageStage stage;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_holder_type", nullable = false)
    private StockHolderType sourceHolderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_location")
    private Location sourceLocation;

    @ManyToOne
    @JoinColumn(name = "source_partner_id")
    private ProductionPartner sourcePartner;

    @ManyToOne
    @JoinColumn(name = "responsible_partner_id")
    private ProductionPartner responsiblePartner;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "wastage_date", nullable = false)
    private LocalDate wastageDate;

    @Column(nullable = false, length = 1000)
    private String reason;

    public Wastage() {
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

    public ProductionPartner getSourcePartner() {
        return sourcePartner;
    }

    public void setSourcePartner(ProductionPartner sourcePartner) {
        this.sourcePartner = sourcePartner;
    }

    public ProductionPartner getResponsiblePartner() {
        return responsiblePartner;
    }

    public void setResponsiblePartner(ProductionPartner responsiblePartner) {
        this.responsiblePartner = responsiblePartner;
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
