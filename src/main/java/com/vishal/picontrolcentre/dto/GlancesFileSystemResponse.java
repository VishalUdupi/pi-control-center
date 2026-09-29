package com.vishal.picontrolcentre.dto;

public record GlancesFileSystemResponse(
        String device_name,
        String fs_type,
        String mnt_point,
        String options,
        long size,
        long used,
        long free,
        double percent,
        String key
) {}
