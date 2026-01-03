package com.yovin.transactionservice.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.yovin.transactionservice.service.TransactionService;
import com.yovin.transactionservice.dto.request.UpdateTransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yovin.transactionservice.dto.request.CreateTransactionRequest;
import com.yovin.transactionservice.dto.response.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import com.yovin.transactionservice.*;




@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*" )
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction( @Valid @RequestBody CreateTransactionRequest request) {
        log.info("Received request to create transaction for user ID: {}", request.getUserId());
        TransactionResponse response = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
        @GetMapping
        public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
            log.info("Received request to fetch all transactions");
            List<TransactionResponse> responses = transactionService.getAllTransactions();
            return ResponseEntity.ok(responses);
        }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        log.info("Received request to fetch transaction with ID: {}", id);
        TransactionResponse response = transactionService.getTransactionId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByUserId(@PathVariable Long userId) {
        log.info("Received request to fetch transactions for user ID: {}", userId);
        List<TransactionResponse> responses = transactionService.getTransactionByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TransactionResponse> updateTransactionStatus(@PathVariable Long id,
            @Valid @RequestBody UpdateTransactionRequest request) {
        log.info("Received request to update transaction status for ID: {} to {}", id, request.getTransactionStatus());
        TransactionResponse response = transactionService.updateTransactionStatus(id, request);
        return ResponseEntity.ok(response);
    }

}
