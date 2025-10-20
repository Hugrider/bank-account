package com.example.bankaccount.dto.accountStatement;

import com.example.bankaccount.dto.operation.OperationDto;

import java.util.List;

/**
 * DTO représentant le relevé d’un compte.
 */
public record AccountStatementDto(
        String accountNumber,
        String accountType,
        Double balance,
        List<OperationDto> operations
) {}
