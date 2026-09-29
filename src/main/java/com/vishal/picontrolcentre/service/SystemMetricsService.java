package com.vishal.picontrolcentre.service;

import com.vishal.picontrolcentre.client.GlancesClient;
import com.vishal.picontrolcentre.dto.GlancesCPUResponse;
import org.springframework.stereotype.Service;

@Service
public class SystemMetricsService {

    private final GlancesClient glancesClient;

    public SystemMetricsService(GlancesClient glancesClient) {
        this.glancesClient = glancesClient;
    }

    public SystemMetricsResponse getCpuStats(GlancesCPUResponse glancesCPUResponse){



    }

}
