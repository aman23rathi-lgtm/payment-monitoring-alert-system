package com.paymentmonitor.services;

import com.paymentmonitor.config.ThresholdConfig;
import com.paymentmonitor.dao.TransactionDao;
import com.paymentmonitor.dto.GatewayStatsResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final TransactionDao transactionDao;
    private final ThresholdConfig thresholdConfig;

    public ReportService(TransactionDao transactionDao, ThresholdConfig thresholdConfig) {
        this.transactionDao = transactionDao;
        this.thresholdConfig = thresholdConfig;
    }

    public List<GatewayStatsResponse> getGatewayReport(LocalDateTime from, LocalDateTime to) {

        boolean hasFrom = from != null;
        boolean hasTo = to != null;

        if (hasFrom != hasTo) {
            throw new IllegalArgumentException("Both 'from' and 'to' must be provided together");
        }

        if (hasFrom && from.isAfter(to)) {
            throw new IllegalArgumentException("'from' must not be after 'to'");
        }

        List<GatewayStatsResponse> stats = hasFrom
                ? transactionDao.getGatewayWiseStats(from, to)
                : transactionDao.getGatewayWiseStats();

        stats.forEach(stat ->
                stat.applyThreshold(thresholdConfig.getThresholdFor(stat.getGateway()))
        );

        return stats;
    }
}
