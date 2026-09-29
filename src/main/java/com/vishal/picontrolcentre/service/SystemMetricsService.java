package com.vishal.picontrolcentre.service;

import com.vishal.picontrolcentre.client.GlancesClient;
import com.vishal.picontrolcentre.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemMetricsService {

    private final GlancesClient glancesClient;

    public SystemMetricsService(GlancesClient glancesClient) {
        this.glancesClient = glancesClient;
    }

    public CpuStatsResponse getCpuStats(){

        GlancesCPUResponse cpuResponseresponse = glancesClient.getCpu();

        int nCores = cpuResponseresponse.cpucore();
        double totalUsage = cpuResponseresponse.total();
        double userUsagePercent = cpuResponseresponse.user();
        double iowait = cpuResponseresponse.iowait();

        return new CpuStatsResponse(nCores,totalUsage,userUsagePercent,iowait);
    }

    public MemoryStatsResponse getMemoryStats(){

        GlancesMemoryResponse memoryResponse = glancesClient.getMemory();

        long total = memoryResponse.total();
        long active = memoryResponse.active();
        long free = memoryResponse.free();
        double percent = memoryResponse.percent();
        long used = memoryResponse.used();

        return new MemoryStatsResponse(total,active,free,percent,used);

    }

    public LoadStatsResponse getLoadStats(){

        GlancesLoadResponse loadResponse = glancesClient.getLoad();

        double min1 = loadResponse.min1();
        double min5 = loadResponse.min5();
        double min15 = loadResponse.min15();

        return new LoadStatsResponse(min1, min5, min15);

    }

    public NetworkStatsResponse getNetworkStats(){

        List<GlancesNetworkResponse> networkResponse = glancesClient.getNetwork();

        GlancesNetworkResponse result = null;

        Optional<GlancesNetworkResponse> response = networkResponse.stream()
                .filter(x -> x.interface_name().equalsIgnoreCase("eth0"))
                .findFirst();

        if(response.isPresent()){
            result = response.get();
        }

        String name = result.interface_name();
        double recvRatePerSec = result.bytes_recv_rate_per_sec();
        double sentRatePerSec = result.bytes_sent_rate_per_sec();

        return new NetworkStatsResponse(name, recvRatePerSec, sentRatePerSec);


    }

}
