package com.example.bankaccount.dto.account;

/**
 * DTO compte bancaire utilisé pour renvoyer les informations
 */
public record AccountDto(
        String number,
        Double balance,
        String type
) {}
