package com.paymentmonitor.dto;

public class GatewayStatsResponse {

    private String gateway;
    private long totalCount;
    private long successCount;
    private long failedCount;
    private double failureRate;
    private double threshold;
    private boolean breached;

    public GatewayStatsResponse(String gateway, long totalCount, long successCount, long failedCount) {
        this.gateway = gateway;
        this.totalCount = totalCount;
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.failureRate = totalCount == 0
                ? 0.0
                : (failedCount * 100.0) / totalCount;
    }

    public void applyThreshold(double threshold) {
        this.threshold = threshold;
        this.breached = this.failureRate > threshold;
    }

    public String getGateway() {
        return gateway;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public long getSuccessCount() {
        return successCount;
    }

    public long getFailedCount() {
        return failedCount;
    }

    public double getFailureRate() {
        return failureRate;
    }

    public double getThreshold() {
        return threshold;
    }

    public boolean isBreached() {
        return breached;
    }
}
