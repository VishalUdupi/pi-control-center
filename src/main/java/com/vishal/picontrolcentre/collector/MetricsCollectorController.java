package com.vishal.picontrolcentre.collector;

import com.vishal.picontrolcentre.dto.CpuStatsResponse;
import com.vishal.picontrolcentre.dto.LoadStatsResponse;
import com.vishal.picontrolcentre.dto.MemoryStatsResponse;
import com.vishal.picontrolcentre.dto.NetworkStatsResponse;
import com.vishal.picontrolcentre.service.SystemMetricsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class MetricsCollectorController {

    private final SystemMetricsService metricsService;


    public MetricsCollectorController(SystemMetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Scheduled(fixedDelayString = "${metrics.collector.interval}")
    public void collectFastMetrics(){
        CpuStatsResponse cpuStats = metricsService.getCpuStats();
        MemoryStatsResponse memoryStats = metricsService.getMemoryStats();
        LoadStatsResponse loadStats = metricsService.getLoadStats();
        NetworkStatsResponse networkStats = metricsService.getNetworkStats();
    }


}
