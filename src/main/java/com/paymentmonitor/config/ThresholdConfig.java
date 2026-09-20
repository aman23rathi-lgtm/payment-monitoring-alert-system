package com.paymentmonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "monitoring.threshold")
public class ThresholdConfig {

    private double defaultValue;
    private Map<String, Double> gateways = Map.of();

    public double getThresholdFor(String gateway) {

        if (gateway == null) {
            return defaultValue;
        }

        return gateways.getOrDefault(gateway.toLowerCase(), defaultValue);
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Map<String, Double> getGateways() {
        return gateways;
    }

    public void setGateways(Map<String, Double> gateways) {
        this.gateways = gateways;
    }
}
