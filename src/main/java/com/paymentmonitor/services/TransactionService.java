package com.paymentmonitor.services;

import com.paymentmonitor.dao.TransactionDao;
import com.paymentmonitor.exception.TransactionNotFoundException;
import com.paymentmonitor.model.Transaction;
import com.paymentmonitor.model.TransactionStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public List<Transaction> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    public Transaction getTransactionById(Long id) {

        Transaction transaction = transactionDao.getTransactionById(id);

        if (transaction == null) {
            throw new TransactionNotFoundException(
                    "Transaction not found with id: " + id
            );
        }

        return transaction;
    }

    public List<Transaction> getTransactionsByStatus(TransactionStatus status) {
        return transactionDao.getTransactionsByStatus(status.name());
    }

    public Long createTransaction(Transaction transaction) {
        return transactionDao.insertTransaction(transaction);
    }

    public void updateTransaction(Long id, Transaction transaction) {

        int rowsUpdated = transactionDao.updateTransaction(id, transaction);

        if (rowsUpdated == 0) {
            throw new TransactionNotFoundException(
                    "Transaction not found with id: " + id
            );
        }
    }

    public void deleteTransaction(Long id) {

        int rowsDeleted = transactionDao.deleteTransaction(id);

        if (rowsDeleted == 0) {
            throw new TransactionNotFoundException(
                    "Transaction not found with id: " + id
            );
        }
    }
}
