package com.yovin.transactionservice.dto.response;

import com.yovin.transactionservice.entity.TransactionStatus;
import com.yovin.transactionservice.entity.TransactionType;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private Long userId;
    private Double amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private String createdAt;
    private String updatedAt;
}
