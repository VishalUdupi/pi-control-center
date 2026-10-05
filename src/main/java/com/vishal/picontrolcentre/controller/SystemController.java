package com.vishal.picontrolcentre.controller;

import com.vishal.picontrolcentre.dto.*;
import com.vishal.picontrolcentre.store.RedisMetricsStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/system")
public class SystemController {

    private final RedisMetricsStore metricsStore;

    public SystemController(RedisMetricsStore metricsStore){
        this.metricsStore = metricsStore;
    }

    @GetMapping("/cpu")
    public ResponseEntity<CpuStatsResponse> getCpu() {
        return ResponseEntity.ok(
                metricsStore.getCpuStats()
        );
    }

    @GetMapping("/memory")
    public ResponseEntity<MemoryStatsResponse> getMemory() {
        return ResponseEntity.ok(
                metricsStore.getMemoryStats()
        );
    }

    @GetMapping("/load")
    public ResponseEntity<LoadStatsResponse> getLoad() {
        return ResponseEntity.ok(
                metricsStore.getLoadStats()
        );
    }

    @GetMapping("/network")
    public ResponseEntity<NetworkStatsResponse> getNetwork() {
        return ResponseEntity.ok(
                metricsStore.getNetworkStats()
        );
    }

    @GetMapping("/fs")
    public ResponseEntity<FileSystemStatsResponse> getFs() {
        return ResponseEntity.ok(
                metricsStore.getFileSystemStats("/dev/sdb2")
        );
    }

    @GetMapping("/sensor")
    public ResponseEntity<SensorStatsResponse> getSensor() {
        return ResponseEntity.ok(
                metricsStore.getSensorStats()
        );
    }

    @GetMapping("/uptime")
    public ResponseEntity<String> getUptime() {
        return ResponseEntity.ok(
                metricsStore.getUptime()
        );
    }

}
