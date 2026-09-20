package com.paymentmonitor.services;

import com.paymentmonitor.dao.AlertDao;
import com.paymentmonitor.dto.GatewayStatsResponse;
import com.paymentmonitor.model.Alert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertDao alertDao;

    public AlertService(AlertDao alertDao) {
        this.alertDao = alertDao;
    }

    public void raiseAlert(GatewayStatsResponse violation) {

        Alert alert = new Alert();
        alert.setGateway(violation.getGateway());
        alert.setTotalCount(violation.getTotalCount());
        alert.setFailedCount(violation.getFailedCount());
        alert.setFailureRate(violation.getFailureRate());
        alert.setThreshold(violation.getThreshold());

        alertDao.insertAlert(alert);
    }

    public List<Alert> getAllAlerts() {
        return alertDao.getAllAlerts();
    }
}
