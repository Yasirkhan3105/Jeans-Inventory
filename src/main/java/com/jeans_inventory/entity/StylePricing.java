package com.jeans_inventory.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "style_pricing",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_pricing_style", columnNames = "style_id")
        }
)
public class StylePricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @Column(name = "cost_per_piece", nullable = false, precision = 19, scale = 2)
    private BigDecimal costPerPiece;

    @Column(name = "selling_price_per_piece", nullable = false, precision = 19, scale = 2)
    private BigDecimal sellingPricePerPiece;

    public StylePricing() {
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

    public BigDecimal getCostPerPiece() {
        return costPerPiece;
    }

    public void setCostPerPiece(BigDecimal costPerPiece) {
        this.costPerPiece = costPerPiece;
    }

    public BigDecimal getSellingPricePerPiece() {
        return sellingPricePerPiece;
    }

    public void setSellingPricePerPiece(BigDecimal sellingPricePerPiece) {
        this.sellingPricePerPiece = sellingPricePerPiece;
    }

    @Transient
    public BigDecimal getProfitPerPiece() {
        if (costPerPiece == null || sellingPricePerPiece == null) {
            return null;
        }

        return sellingPricePerPiece.subtract(costPerPiece);
    }
}
