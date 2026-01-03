package com.yovin.transactionservice.exception;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String message) {
        super(message);
    }

    public InvalidTransactionException(Long id) {
        super("Invalid transaction with ID: " + id);
    }
}
