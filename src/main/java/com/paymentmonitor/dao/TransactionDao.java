package com.paymentmonitor.dao;

import com.paymentmonitor.dto.GatewayStatsResponse;
import com.paymentmonitor.model.Transaction;
import com.paymentmonitor.queries.ReportQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TransactionDao {

    private static final RowMapper<Transaction> TRANSACTION_ROW_MAPPER = (rs, rowNum) -> {

        Transaction transaction = new Transaction();

        transaction.setId(rs.getLong("id"));
        transaction.setTransactionId(rs.getString("transaction_id"));
        transaction.setGateway(rs.getString("gateway"));
        transaction.setPaymentOption(rs.getString("payment_option"));
        transaction.setBank(rs.getString("bank"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setStatus(rs.getString("status"));
        transaction.setResponseCode(rs.getString("response_code"));

        if (rs.getTimestamp("request_time") != null) {
            transaction.setRequestTime(
                    rs.getTimestamp("request_time").toLocalDateTime()
            );
        }

        if (rs.getTimestamp("created_at") != null) {
            transaction.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }

        return transaction;
    };

    private final JdbcTemplate jdbcTemplate;

    public TransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transaction> getAllTransactions() {

        return jdbcTemplate.query(ReportQueries.GET_ALL_TRANSACTIONS, TRANSACTION_ROW_MAPPER);
    }

    public Transaction getTransactionById(Long id) {

        List<Transaction> transactions = jdbcTemplate.query(
                ReportQueries.GET_TRANSACTION_BY_ID,
                TRANSACTION_ROW_MAPPER,
                id
        );

        return transactions.isEmpty() ? null : transactions.get(0);
    }

    public List<Transaction> getTransactionsByStatus(String status) {

        return jdbcTemplate.query(
                ReportQueries.GET_TRANSACTIONS_BY_STATUS,
                TRANSACTION_ROW_MAPPER,
                status
        );
    }

    public List<GatewayStatsResponse> getGatewayWiseStats() {

        return jdbcTemplate.query(ReportQueries.GET_GATEWAY_WISE_STATS, (rs, rowNum) ->
                new GatewayStatsResponse(
                        rs.getString("gateway"),
                        rs.getLong("total_count"),
                        rs.getLong("success_count"),
                        rs.getLong("failed_count")
                )
        );
    }

    public Long insertTransaction(Transaction transaction) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(ReportQueries.INSERT_TRANSACTION, new String[]{"id"});

            ps.setString(1, transaction.getTransactionId());
            ps.setString(2, transaction.getGateway());
            ps.setString(3, transaction.getPaymentOption());
            ps.setString(4, transaction.getBank());
            ps.setBigDecimal(5, transaction.getAmount());
            ps.setString(6, transaction.getStatus());
            ps.setString(7, transaction.getResponseCode());

            if (transaction.getRequestTime() != null) {
                ps.setObject(8, transaction.getRequestTime());
            } else {
                ps.setObject(8, null);
            }

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int updateTransaction(Long id, Transaction transaction) {

        return jdbcTemplate.update(
                ReportQueries.UPDATE_TRANSACTION,
                transaction.getGateway(),
                transaction.getPaymentOption(),
                transaction.getBank(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getResponseCode(),
                transaction.getRequestTime(),
                id
        );
    }

    public int deleteTransaction(Long id) {

        return jdbcTemplate.update(
                ReportQueries.DELETE_TRANSACTION,
                id
        );
    }
}