package com.jeans_inventory.repository;

import com.jeans_inventory.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyleRepository extends JpaRepository<Style, Long> {
}