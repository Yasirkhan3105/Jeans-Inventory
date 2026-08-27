package com.jeans_inventory.dto;

import java.math.BigDecimal;

public record DashboardSummary(
        long totalStyles,
        long shopStock,
        long finishingStock,
        long fabricatorStock,
        long washerStock,
        long shopAssortmentPieces,
        long samplePieces,
        long wastagePieces,
        long alterPieces,
        java.math.BigDecimal normalStockValue,
        java.math.BigDecimal fabricatorStockValue,
        java.math.BigDecimal washerStockValue,
        java.math.BigDecimal totalInventoryValue
) {
}
