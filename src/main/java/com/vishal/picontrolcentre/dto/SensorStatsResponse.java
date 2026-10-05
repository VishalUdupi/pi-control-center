package com.vishal.picontrolcentre.dto;

public record SensorStatsResponse(
        String label,
        String unit,
        int value,
        String type,
        String key
) {}