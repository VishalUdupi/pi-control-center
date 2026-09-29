package com.vishal.picontrolcentre.collector;

import com.vishal.picontrolcentre.dto.CpuStatsResponse;
import com.vishal.picontrolcentre.dto.LoadStatsResponse;
import com.vishal.picontrolcentre.dto.MemoryStatsResponse;
import com.vishal.picontrolcentre.dto.NetworkStatsResponse;
import com.vishal.picontrolcentre.service.SystemMetricsService;
import com.vishal.picontrolcentre.store.RedisMetricsStore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricsCollector {

    private final SystemMetricsService metricsService;

    private final RedisMetricsStore metricsStore;

    public MetricsCollector(SystemMetricsService metricsService, RedisMetricsStore metricsStore) {
        this.metricsService = metricsService;
        this.metricsStore = metricsStore;
    }

    @Scheduled(fixedDelayString = "${metrics.collector.interval}")
    public void collectFastMetrics(){
        System.out.println("Collector running");
        CpuStatsResponse cpuStats = metricsService.getCpuStats();
        MemoryStatsResponse memoryStats = metricsService.getMemoryStats();
        LoadStatsResponse loadStats = metricsService.getLoadStats();
        NetworkStatsResponse networkStats = metricsService.getNetworkStats();
        metricsStore.saveCpuStats(cpuStats);
    }


}
