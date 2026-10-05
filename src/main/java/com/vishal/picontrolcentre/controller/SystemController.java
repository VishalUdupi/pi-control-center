package com.vishal.picontrolcentre.controller;

import com.vishal.picontrolcentre.dto.CpuStatsResponse;
import com.vishal.picontrolcentre.store.RedisMetricsStore;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CpuStatsResponse> getCpu(){
        return new ResponseEntity<>(
                metricsStore.getCpuStats(),
                HttpStatus.OK
        );
    }

}
