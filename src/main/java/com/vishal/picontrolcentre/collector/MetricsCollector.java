package com.vishal.picontrolcentre.collector;

import com.vishal.picontrolcentre.monitor.GlancesStatusMonitor;
import com.vishal.picontrolcentre.service.SystemMetricsService;
import com.vishal.picontrolcentre.status.GlancesStatus;
import com.vishal.picontrolcentre.store.RedisMetricsStore;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricsCollector {

    private final SystemMetricsService metricsService;

    private final RedisMetricsStore metricsStore;

    private final GlancesStatusMonitor glancesStatusMonitor;

    public MetricsCollector(SystemMetricsService metricsService, RedisMetricsStore metricsStore, GlancesStatusMonitor glancesStatusMonitor) {
        this.metricsService = metricsService;
        this.metricsStore = metricsStore;
        this.glancesStatusMonitor = glancesStatusMonitor;
    }

    @Scheduled(fixedDelayString = "${fast.metrics.collector.interval}")
    public void collectFastMetrics(){
        if(glancesStatusMonitor.getStatus() == GlancesStatus.UP){
            metricsStore.saveCpuStats(metricsService.getCpuStats());
            metricsStore.saveMemoryStats(metricsService.getMemoryStats());
            metricsStore.saveLoadStats(metricsService.getLoadStats());
            metricsStore.saveNetworkStats(metricsService.getNetworkStats());
        }
    }

    @Scheduled(fixedDelayString = "${sensor.metrics.collector.interval}")
    public void collectSensorMetrics(){
        if(glancesStatusMonitor.getStatus() == GlancesStatus.UP){
            metricsStore.saveSensorStats(metricsService.getSensorStats());
        }
    }

    @Scheduled(fixedDelayString = "${slow.metrics.collector.interval}")
    public void collectSlowMetrics(){
        if(glancesStatusMonitor.getStatus() == GlancesStatus.UP){
            metricsStore.saveFileSystemStats(metricsService.getFileSystemStats());
            metricsStore.saveUptime(metricsService.getUptime());
        }
    }




}
