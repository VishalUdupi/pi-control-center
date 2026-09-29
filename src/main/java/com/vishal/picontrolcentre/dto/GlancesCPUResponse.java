package com.vishal.picontrolcentre.dto;

public record GlancesCPUResponse(
        double total,
        double user,
        double nice,
        double system,
        double idle,
        double iowait,
        double irq,
        double steal,
        double guest,
        long ctx_switches,
        long interrupts,
        long soft_interrupts,
        long syscalls,
        int cpucore,
        double time_since_update,
        long ctx_switches_gauge,
        double ctx_switches_rate_per_sec,
        long interrupts_gauge,
        double interrupts_rate_per_sec,
        long soft_interrupts_gauge,
        double soft_interrupts_rate_per_sec,
        long syscalls_gauge,
        double syscalls_rate_per_sec
) {}
