package com.yovin.transactionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yovin.transactionservice.service.NotificationClient;
import com.yovin.transactionservice.entity.Transaction;
import com.yovin.transactionservice.dto.request.CreateTransactionRequest;
import com.yovin.transactionservice.dto.request.UpdateTransactionRequest;
import com.yovin.transactionservice.dto.response.TransactionResponse;
import com.yovin.transactionservice.repository.TransactionRepository;
import com.yovin.transactionservice.entity.TransactionStatus;
import com.yovin.transactionservice.exception.TransactionNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionService {

    private final NotificationClient notificationClient;
    private final TransactionRepository transactionRepository;

    private static final double HIGH_VALUE_THRESHOLD = 5000.0;

    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        log.info("Creating transaction for user ID: {}", request.getUserId());

        Transaction transaction = new Transaction();
        transaction.setUserId(request.getUserId());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setStatus(TransactionStatus.PENDING);
        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction created with ID: {}", savedTransaction.getId());

        if (savedTransaction.getAmount() >= HIGH_VALUE_THRESHOLD) {
            log.info("High value transaction detected for transaction ID: {}", savedTransaction.getId());
            notificationClient.sendHighValueTransactionNotification(
                    savedTransaction.getUserId(),
                    savedTransaction.getId(), 
                    savedTransaction.getAmount().doubleValue());
        }
        return mapToResponse(savedTransaction);
    }

    @Transactional(readOnly = true)
    public TransactionResponse getTransactionId(Long id) {
        log.info("Fetching transaction with ID: {}", id);
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        return mapToResponse(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllTransactions() {
        log.info("Fetching all transactions");
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionByUserId(Long userId) {
        log.info("Fetching transaction for user ID: {}", userId);

        return transactionRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse updateTransactionStatus(Long id, UpdateTransactionRequest request) {
        log.info("Updating transaction status for ID: {} to {}", id, request.getTransactionStatus());
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        TransactionStatus oldStatus = transaction.getStatus();
        transaction.setStatus(request.getTransactionStatus());
        Transaction updatedTransaction = transactionRepository.save(transaction);
        log.info("Transaction ID: {} status updated from {} to {}", id, oldStatus,
                updatedTransaction.getStatus());

        if (updatedTransaction.getStatus() == TransactionStatus.COMPLETED) {
            log.info("Sending transaction complete notification for transaction ID: {}", id);
            notificationClient.sendTransactionCompleteNotification(
                    updatedTransaction.getUserId(),
                    updatedTransaction.getId(),
                    updatedTransaction.getAmount().doubleValue());
        }

        return mapToResponse(updatedTransaction);
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .userId(transaction.getUserId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .transactionStatus(transaction.getStatus())
                .createdAt(transaction.getCreatedAt().toString())
                .updatedAt(transaction.getUpdatedAt().toString())
                .build();
    }
}
