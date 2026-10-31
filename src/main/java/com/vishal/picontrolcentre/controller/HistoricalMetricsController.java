package com.vishal.picontrolcentre.controller;

import com.vishal.picontrolcentre.entity.*;
import com.vishal.picontrolcentre.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("app/historical/system")
public class HistoricalMetricsController {

    private final CpuRepository cpuRepository;
    private final LoadRepository loadRepository;
    private final MemoryRepository memoryRepository;
    private final NetworkRepository networkRepository;
    private final SensorRepository sensorRepository;


    public HistoricalMetricsController(CpuRepository cpuRepository, LoadRepository loadRepository, MemoryRepository memoryRepository, NetworkRepository networkRepository, SensorRepository sensorRepository) {
        this.cpuRepository = cpuRepository;
        this.loadRepository = loadRepository;
        this.memoryRepository = memoryRepository;
        this.networkRepository = networkRepository;
        this.sensorRepository = sensorRepository;
    }

    @GetMapping("/cpu")
    public ResponseEntity<List<CpuHistory>> getHCpu(){
        List<CpuHistory> cpuHistory = cpuRepository.findAll();
        return ResponseEntity.ok(cpuHistory);
    }

    @GetMapping("/load")
    public ResponseEntity<List<LoadHistory>> getHLoad() {
        List<LoadHistory> loadHistory = loadRepository.findAll();
        return ResponseEntity.ok(loadHistory);
    }

    @GetMapping("/memory")
    public ResponseEntity<List<MemoryHistory>> getHMemory() {
        List<MemoryHistory> memoryHistory = memoryRepository.findAll();
        return ResponseEntity.ok(memoryHistory);
    }

    @GetMapping("/network")
    public ResponseEntity<List<NetworkHistory>> getHNetwork() {
        List<NetworkHistory> networkHistory = networkRepository.findAll();
        return ResponseEntity.ok(networkHistory);
    }

    @GetMapping("/sensor")
    public ResponseEntity<List<SensorHistory>> getHSensor() {
        List<SensorHistory> sensorHistory = sensorRepository.findAll();
        return ResponseEntity.ok(sensorHistory);
    }

}
