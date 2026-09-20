package com.paymentmonitor.dto;

public class TransactionResponse {

    private Long id;
    private String message;

    public TransactionResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}