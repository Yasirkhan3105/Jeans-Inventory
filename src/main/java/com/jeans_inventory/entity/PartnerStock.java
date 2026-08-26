package com.jeans_inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "partner_stock",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_partner_style",
                        columnNames = {"partner_id", "style_id"}
                )
        }
)
public class PartnerStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "partner_id", nullable = false)
    private ProductionPartner partner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @Column(nullable = false)
    private Integer quantity;

    public PartnerStock() {
    }

    public Long getId() {
        return id;
    }

    public ProductionPartner getPartner() {
        return partner;
    }

    public void setPartner(ProductionPartner partner) {
        this.partner = partner;
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
}
