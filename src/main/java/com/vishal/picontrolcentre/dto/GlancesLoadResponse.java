package com.vishal.picontrolcentre.dto;

public record GlancesLoadResponse(
        double min1,
        double min5,
        double min15,
        int cpucore
) {}
