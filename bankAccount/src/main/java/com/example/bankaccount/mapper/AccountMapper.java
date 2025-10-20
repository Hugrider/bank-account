package com.example.bankaccount.mapper;

import com.example.bankaccount.dto.account.AccountDto;
import com.example.bankaccount.model.Account;
import com.example.bankaccount.model.CheckingAccount;
import org.springframework.stereotype.Component;

/**
 * Mapper d'un compte bancaire vers DTO
 */
@Component
public class AccountMapper {

    /**
     * Mapping d'un {@link Account} vers {@link AccountDto}
     * @param account   Entité compte bancaire
     * @return  AccountDto avec les données du compte bancaire
     */
    public AccountDto toDto(Account account) {
        String type = (account instanceof CheckingAccount) ? "CHECKING" : "SAVINGS";
        return new AccountDto(
                account.getNumber(),
                account.getBalance(),
                type
        );
    }
}
