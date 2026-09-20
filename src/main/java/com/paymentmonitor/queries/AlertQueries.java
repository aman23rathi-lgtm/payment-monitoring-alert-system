package com.paymentmonitor.queries;

public class AlertQueries {
    private AlertQueries() {
        // Utility class
    }

    public static final String INSERT_ALERT = """
            INSERT INTO alerts
            (gateway, total_count, failed_count, failure_rate, threshold)
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String GET_ALL_ALERTS = """
            SELECT id,
                   gateway,
                   total_count,
                   failed_count,
                   failure_rate,
                   threshold,
                   created_at
            FROM alerts
            ORDER BY created_at DESC
            """;
}
