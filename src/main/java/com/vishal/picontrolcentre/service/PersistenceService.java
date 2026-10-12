package com.vishal.picontrolcentre.service;

import com.vishal.picontrolcentre.dto.*;
import com.vishal.picontrolcentre.entity.*;
import com.vishal.picontrolcentre.repository.*;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {

    private final CpuRepository cpuRepository;
    private final MemoryRepository memoryRepository;
    private final LoadRepository loadRepository;
    private final NetworkRepository networkRepository;
    private final SensorRepository sensorRepository;


    public PersistenceService(
            CpuRepository cpuRepository,
            MemoryRepository memoryRepository,
            LoadRepository loadRepository,
            NetworkRepository networkRepository,
            SensorRepository sensorRepository) {

        this.cpuRepository = cpuRepository;
        this.memoryRepository = memoryRepository;
        this.loadRepository = loadRepository;
        this.networkRepository = networkRepository;
        this.sensorRepository = sensorRepository;
    }

    public void persistCpuStats(CpuStatsResponse response){
        CpuHistory cpuHistory = new CpuHistory(
                response.cores(),
                response.total(),
                response.user(),
                response.iowait()
        );
        cpuRepository.save(cpuHistory);
    }


    public void persistMemoryStats(MemoryStatsResponse response) {
        MemoryHistory memoryHistory = new MemoryHistory(
                response.total(),
                response.free(),
                response.percent(),
                response.used()
        );

        memoryRepository.save(memoryHistory);
    }

    public void persistLoadStats(LoadStatsResponse response) {
        LoadHistory loadHistory = new LoadHistory(
                response.min1(),
                response.min5(),
                response.min15()
        );

        loadRepository.save(loadHistory);
    }

    public void persistNetworkStats(NetworkStatsResponse response) {
        NetworkHistory networkHistory = new NetworkHistory(
                response.name(),
                response.recvRatePerSec(),
                response.sentRatePerSec()
        );

        networkRepository.save(networkHistory);
    }

    public void persistSensorStats(SensorStatsResponse response) {
        SensorHistory sensorHistory = new SensorHistory(
                response.label(),
                response.unit(),
                response.value(),
                response.type(),
                response.key()
        );

        sensorRepository.save(sensorHistory);
    }




}
