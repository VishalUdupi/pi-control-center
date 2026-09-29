package com.vishal.picontrolcentre.dto;

public record GlancesNetworkResponse(
        long bytes_sent,
        long bytes_recv,
        long speed,
        String key,
        String interface_name,
        String alias,
        long bytes_all,
        double time_since_update,
        long bytes_recv_gauge,
        double bytes_recv_rate_per_sec,
        long bytes_sent_gauge,
        double bytes_sent_rate_per_sec,
        long bytes_all_gauge,
        double bytes_all_rate_per_sec
) {}
