package com.vishal.picontrolcentre.monitor;

import com.vishal.picontrolcentre.client.GlancesClient;
import com.vishal.picontrolcentre.dto.GlancesStatusResponse;
import com.vishal.picontrolcentre.status.GlancesStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GlancesStatusMonitor {

    private final GlancesClient client;

    private GlancesStatus currentStatus = GlancesStatus.DOWN;

    public GlancesStatusMonitor(GlancesClient glancesClient) {
        this.client = glancesClient;
    }

    public void checkStatus(){
        GlancesStatusResponse response = client.getStatus();

        if(client.getStatus() != null && response.version() != null){
            currentStatus = GlancesStatus.UP;
        }
        else {
            currentStatus = GlancesStatus.DOWN;
        }
    }

    public GlancesStatus getStatus(){
        return currentStatus;
    }

    @Scheduled(fixedRate = 2 * 60 * 1000)
    public void monitorGlances() {
        checkStatus();
    }


}
