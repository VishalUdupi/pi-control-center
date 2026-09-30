package com.vishal.picontrolcentre.collector;

import com.vishal.picontrolcentre.service.SystemMetricsService;
import com.vishal.picontrolcentre.store.RedisMetricsStore;
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

    @Scheduled(fixedDelayString = "${fast.metrics.collector.interval}")
    public void collectFastMetrics(){
        System.out.println("Collector running");
        metricsStore.saveCpuStats(metricsService.getCpuStats());
        metricsStore.saveMemoryStats(metricsService.getMemoryStats());
        metricsStore.saveLoadStats(metricsService.getLoadStats());
        metricsStore.saveNetworkStats(metricsService.getNetworkStats());

    }

    @Scheduled(fixedDelayString = "${sensor.metrics.collector.interval}")
    public void collectSensorMetrics(){
        metricsStore.saveSensorStats(metricsService.getSensorStats());
    }

    @Scheduled(fixedDelayString = "${slow.metrics.collector.interval}")
    public void collectSlowMetrics(){
        metricsStore.saveFileSystemStats(metricsService.getFileSystemStats());
        metricsStore.saveUptime(metricsService.getUptime());
    }




}
