package com.vishal.picontrolcentre.dto;

public record FileSystemStatsResponse(
        String deviceName,
        String fsType,
        String mntPoint,
        String options,
        long size,
        long used,
        long free,
        double percent,
        String key
) {}
