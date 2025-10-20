package com.example.bankaccount.dto.operation;

import com.example.bankaccount.model.OperationType;

import java.time.LocalDateTime;

/**
 * DTO représentant une opération pour le relevé de compte
 */
public record OperationDto(
        Long id,
        Double amount,
        OperationType type,
        LocalDateTime date
) {}
