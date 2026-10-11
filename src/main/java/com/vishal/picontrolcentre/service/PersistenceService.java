package com.vishal.picontrolcentre.service;

import com.vishal.picontrolcentre.dto.CpuStatsResponse;
import com.vishal.picontrolcentre.entity.CpuHistory;
import com.vishal.picontrolcentre.repository.CpuRepository;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {

    private final CpuRepository cpuRepository;


    public PersistenceService(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
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


}
