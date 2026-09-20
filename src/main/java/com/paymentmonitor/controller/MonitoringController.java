package com.paymentmonitor.controller;

import com.paymentmonitor.dto.GatewayStatsResponse;
import com.paymentmonitor.services.MonitoringService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/gateway-stats")
    public List<GatewayStatsResponse> getGatewayStats() {
        return monitoringService.getGatewayWiseStats();
    }

    @GetMapping("/violations")
    public List<GatewayStatsResponse> getViolations() {
        return monitoringService.getGatewayViolations();
    }
}
