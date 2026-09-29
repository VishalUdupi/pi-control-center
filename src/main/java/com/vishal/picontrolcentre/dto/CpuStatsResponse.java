package com.vishal.picontrolcentre.dto;

public record CpuStatsResponse(
        int cores,
        double total,
        double user,
        double iowait
) {}
