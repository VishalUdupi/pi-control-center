package com.vishal.picontrolcentre.dto;

public record FileSystemStatsResponse(
        String deviceName,
        String mntPoint,
        long size,
        long used,
        long free,
        double percent
) {}
