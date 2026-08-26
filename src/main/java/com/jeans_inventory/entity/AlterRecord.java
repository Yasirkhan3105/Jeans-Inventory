package com.jeans_inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "alter_records")
public class AlterRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "fault_type", nullable = false)
    private AlterFaultType faultType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "responsible_partner_id", nullable = false)
    private ProductionPartner responsiblePartner;

    @Column(name = "recorded_date", nullable = false)
    private LocalDate recordedDate;

    @Column(length = 1000)
    private String remarks;

    public AlterRecord() {
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

    public ProductionPartner getResponsiblePartner() {
        return responsiblePartner;
    }

    public void setResponsiblePartner(ProductionPartner responsiblePartner) {
        this.responsiblePartner = responsiblePartner;
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
