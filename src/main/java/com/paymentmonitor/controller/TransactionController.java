package com.paymentmonitor.controller;

import com.paymentmonitor.dto.TransactionResponse;
import com.paymentmonitor.model.Transaction;
import com.paymentmonitor.model.TransactionStatus;
import com.paymentmonitor.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/status/{status}")
    public List<Transaction> getTransactionsByStatus(@PathVariable TransactionStatus status) {
        return transactionService.getTransactionsByStatus(status);
    }

    @PostMapping
    public TransactionResponse createTransaction(@Valid @RequestBody Transaction transaction) {

        Long id = transactionService.createTransaction(transaction);

        return new TransactionResponse(
                id,
                "Transaction created successfully"
        );
    }

    @PutMapping("/{id}")
    public String updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody Transaction transaction) {

        transactionService.updateTransaction(id, transaction);

        return "Transaction updated successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id) {

        transactionService.deleteTransaction(id);

        return "Transaction deleted successfully";
    }
}