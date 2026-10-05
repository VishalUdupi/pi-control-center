package com.vishal.picontrolcentre.dto;

public record MemoryStatsResponse(
        long total,
        long free,
        double percent,
        long used
) {}
