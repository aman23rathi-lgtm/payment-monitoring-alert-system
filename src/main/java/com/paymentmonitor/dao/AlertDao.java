package com.paymentmonitor.dao;

import com.paymentmonitor.model.Alert;
import com.paymentmonitor.queries.AlertQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlertDao {

    private static final RowMapper<Alert> ALERT_ROW_MAPPER = (rs, rowNum) -> {

        Alert alert = new Alert();

        alert.setId(rs.getLong("id"));
        alert.setGateway(rs.getString("gateway"));
        alert.setTotalCount(rs.getLong("total_count"));
        alert.setFailedCount(rs.getLong("failed_count"));
        alert.setFailureRate(rs.getDouble("failure_rate"));
        alert.setThreshold(rs.getDouble("threshold"));

        if (rs.getTimestamp("created_at") != null) {
            alert.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }

        return alert;
    };

    private final JdbcTemplate jdbcTemplate;

    public AlertDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertAlert(Alert alert) {

        jdbcTemplate.update(
                AlertQueries.INSERT_ALERT,
                alert.getGateway(),
                alert.getTotalCount(),
                alert.getFailedCount(),
                alert.getFailureRate(),
                alert.getThreshold()
        );
    }

    public List<Alert> getAllAlerts() {
        return jdbcTemplate.query(AlertQueries.GET_ALL_ALERTS, ALERT_ROW_MAPPER);
    }
}
