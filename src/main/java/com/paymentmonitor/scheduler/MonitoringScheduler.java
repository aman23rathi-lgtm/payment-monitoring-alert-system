package com.paymentmonitor.scheduler;

import com.paymentmonitor.dto.GatewayStatsResponse;
import com.paymentmonitor.services.MonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonitoringScheduler {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringScheduler.class);

    private final MonitoringService monitoringService;

    public MonitoringScheduler(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Scheduled(fixedRateString = "${monitoring.schedule.rate}")
    public void checkGatewayThresholds() {

        List<GatewayStatsResponse> violations = monitoringService.getGatewayViolations();

        if (violations.isEmpty()) {
            logger.info("Scheduled monitoring check: no threshold violations found");
            return;
        }

        for (GatewayStatsResponse violation : violations) {
            logger.warn(
                    "ALERT: Gateway '{}' failure rate {}% exceeds threshold {}% (total={}, failed={})",
                    violation.getGateway(),
                    violation.getFailureRate(),
                    violation.getThreshold(),
                    violation.getTotalCount(),
                    violation.getFailedCount()
            );
        }
    }
}
