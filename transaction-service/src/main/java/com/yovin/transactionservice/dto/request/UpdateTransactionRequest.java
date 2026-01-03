package com.yovin.transactionservice.dto.request;
import com.yovin.transactionservice.entity.TransactionStatus;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransactionRequest {
    @NotNull(message = "Transaction status is required")
    private TransactionStatus  transactionStatus;
}