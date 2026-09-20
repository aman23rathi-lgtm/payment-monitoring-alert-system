package com.paymentmonitor.controller;

import com.paymentmonitor.dto.GatewayStatsResponse;
import com.paymentmonitor.services.AlertService;
import com.paymentmonitor.services.MonitoringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private static final int RECENT_ALERTS_LIMIT = 10;

    private final MonitoringService monitoringService;
    private final AlertService alertService;

    public DashboardController(MonitoringService monitoringService, AlertService alertService) {
        this.monitoringService = monitoringService;
        this.alertService = alertService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        List<GatewayStatsResponse> gatewayStats = monitoringService.getGatewayWiseStats();

        long totalTransactions = gatewayStats.stream().mapToLong(GatewayStatsResponse::getTotalCount).sum();
        long successCount = gatewayStats.stream().mapToLong(GatewayStatsResponse::getSuccessCount).sum();
        long failedCount = gatewayStats.stream().mapToLong(GatewayStatsResponse::getFailedCount).sum();
        long violationCount = gatewayStats.stream().filter(GatewayStatsResponse::isBreached).count();

        model.addAttribute("gatewayStats", gatewayStats);
        model.addAttribute("totalTransactions", totalTransactions);
        model.addAttribute("successCount", successCount);
        model.addAttribute("failedCount", failedCount);
        model.addAttribute("violationCount", violationCount);
        model.addAttribute("alerts", alertService.getAllAlerts().stream().limit(RECENT_ALERTS_LIMIT).toList());

        return "dashboard";
    }
}
