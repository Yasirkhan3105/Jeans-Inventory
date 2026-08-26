package com.jeans_inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "samples",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_sample_style_location",
                        columnNames = {"style_id", "location"}
                )
        }
)
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SampleLocation location;

    @Column(nullable = false)
    private Integer quantity;

    public Sample() {
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

    public SampleLocation getLocation() {
        return location;
    }

    public void setLocation(SampleLocation location) {
        this.location = location;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
