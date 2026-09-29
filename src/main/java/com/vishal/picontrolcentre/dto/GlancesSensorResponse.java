package com.vishal.picontrolcentre.dto;

public record GlancesSensorResponse(
        String label,
        String unit,
        int value,
        Integer warning,
        Integer critical,
        String type,
        String key
) {}
