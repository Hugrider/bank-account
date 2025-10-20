package com.example.bankaccount.dto.operation;

/**
 * DTO pour les opérations de dépôt et de retrait
 */
public record AddOperationDto(
        String accountNumber,
        Double amount
) {}
