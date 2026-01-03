package com.yovin.transactionservice.dto.request;

import com.yovin.transactionservice.entity.TransactionType;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {
    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", inclusive = false, message = "Amount must be greater than 0")
    @DecimalMax(value = "10000.00", message = "Amount must be less than or equal to 10,000")
    private Double amount;

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;
}
