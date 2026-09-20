package com.paymentmonitor.queries;

public class ReportQueries {
    private ReportQueries() {
        // Utility class
    }

    public static final String GET_ALL_TRANSACTIONS = """
            SELECT id,
                   transaction_id,
                   gateway,
                   payment_option,
                   bank,
                   amount,
                   status,
                   response_code,
                   request_time,
                   created_at
            FROM transactions
            ORDER BY request_time DESC
            """;

    public static final String GET_TRANSACTION_BY_ID = """
        SELECT id,
               transaction_id,
               gateway,
               payment_option,
               bank,
               amount,
               status,
               response_code,
               request_time,
               created_at
        FROM transactions
        WHERE id = ?
        """;

    public static final String INSERT_TRANSACTION = """
        INSERT INTO transactions
        (transaction_id,
         gateway,
         payment_option,
         bank,
         amount,
         status,
         response_code,
         request_time)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String UPDATE_TRANSACTION = """
        UPDATE transactions
        SET gateway = ?,
            payment_option = ?,
            bank = ?,
            amount = ?,
            status = ?,
            response_code = ?,
            request_time = ?
        WHERE id = ?
        """;

    public static final String DELETE_TRANSACTION = """
        DELETE FROM transactions
        WHERE id = ?
        """;

    public static final String GET_TRANSACTIONS_BY_STATUS = """
        SELECT id,transaction_id,gateway,
               payment_option,
               bank,
               amount,
               status,
               response_code,
               request_time,
               created_at
        FROM transactions
        WHERE status = ?
        ORDER BY request_time DESC
        """;

    public static final String GET_GATEWAY_WISE_STATS = """
        SELECT gateway,
               COUNT(*) AS total_count,
               SUM(CASE WHEN status = 'SUCCESS' THEN 1 ELSE 0 END) AS success_count,
               SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END) AS failed_count
        FROM transactions
        GROUP BY gateway
        """;
}
