package com.jeans_inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "shop_assortment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_style_size",
                        columnNames = {"style_id", "jeans_size"}
                )
        }
)
public class ShopAssortment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @Column(name = "jeans_size", nullable = false)
    private Integer size;

    @Column(nullable = false)
    private Integer quantity;

    public ShopAssortment() {
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
