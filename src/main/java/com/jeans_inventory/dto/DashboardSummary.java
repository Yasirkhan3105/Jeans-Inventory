package com.jeans_inventory.dto;

public record DashboardSummary(
        long totalStyles,
        long shopStock,
        long finishingStock,
        long fabricatorStock,
        long washerStock,
        long shopAssortmentPieces,
        long samplePieces,
        long wastagePieces,
        long alterPieces
) {
}
