package com.vishal.picontrolcentre.service;

import com.vishal.picontrolcentre.client.GlancesClient;
import com.vishal.picontrolcentre.dto.CpuStatsResponse;
import com.vishal.picontrolcentre.dto.GlancesCPUResponse;
import org.springframework.stereotype.Service;

@Service
public class SystemMetricsService {

    private final GlancesClient glancesClient;

    public SystemMetricsService(GlancesClient glancesClient) {
        this.glancesClient = glancesClient;
    }

    public CpuStatsResponse getCpuStats(){

        GlancesCPUResponse response = glancesClient.getCpu();

        int nCores = response.cpucore();
        double totalUsage = response.total();
        double userUsagePercent = response.user();
        double iowait = response.iowait();

        return new CpuStatsResponse(nCores,totalUsage,userUsagePercent,iowait);
    }

}
