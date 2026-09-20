package com.paymentmonitor.services;

import com.paymentmonitor.config.ThresholdConfig;
import com.paymentmonitor.dao.TransactionDao;
import com.paymentmonitor.dto.GatewayStatsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringService {

    private final TransactionDao transactionDao;
    private final ThresholdConfig thresholdConfig;

    public MonitoringService(TransactionDao transactionDao, ThresholdConfig thresholdConfig) {
        this.transactionDao = transactionDao;
        this.thresholdConfig = thresholdConfig;
    }

    public List<GatewayStatsResponse> getGatewayWiseStats() {

        List<GatewayStatsResponse> stats = transactionDao.getGatewayWiseStats();

        stats.forEach(stat ->
                stat.applyThreshold(thresholdConfig.getThresholdFor(stat.getGateway()))
        );

        return stats;
    }

    public List<GatewayStatsResponse> getGatewayViolations() {

        return getGatewayWiseStats().stream()
                .filter(GatewayStatsResponse::isBreached)
                .toList();
    }
}
