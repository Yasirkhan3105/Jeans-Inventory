package com.jeans_inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "manufacturing_partners",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_partner_type_name",
                        columnNames = {"partner_type", "name"}
                )
        }
)
public class ProductionPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "partner_type", nullable = false)
    private PartnerType partnerType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active = true;

    public ProductionPartner() {
    }

    public Long getId() {
        return id;
    }

    public PartnerType getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(PartnerType partnerType) {
        this.partnerType = partnerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
