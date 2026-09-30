package com.vishal.picontrolcentre.dto;

public record SensorStatsResponse(
        String label,
        String unit,
        int value,
        Integer warning,
        Integer critical,
        String type,
        String key
) {}