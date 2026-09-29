package com.vishal.picontrolcentre.controller;

import com.vishal.picontrolcentre.dto.CpuStatsResponse;
import com.vishal.picontrolcentre.service.SystemMetricsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/system")
public class SystemController {

    private final SystemMetricsService service;


    public SystemController(SystemMetricsService service) {
        this.service = service;
    }

    @GetMapping("/cpu")
    public ResponseEntity<CpuStatsResponse> CpuStatus(){
        return new ResponseEntity<>(
                service.getCpuStats(),
                HttpStatus.OK
                );
    }

}
