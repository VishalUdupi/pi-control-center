package com.vishal.picontrolcentre.dto;

public record NetworkStatsResponse(
        String name,
        double recvRatePerSec,
        double sentRatePerSec
) {}
