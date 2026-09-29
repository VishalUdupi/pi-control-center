package com.vishal.picontrolcentre.dto;

public record GlancesMemoryResponse(
        long total,
        long available,
        double percent,
        long used,
        long free,
        long active,
        long inactive,
        long buffers,
        long cached,
        long shared,
        double percent_min,
        double percent_max,
        double percent_mean
) {}
