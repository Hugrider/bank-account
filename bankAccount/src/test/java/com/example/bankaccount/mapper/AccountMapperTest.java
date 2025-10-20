package com.example.bankaccount.mapper;

import com.example.bankaccount.dto.account.AccountDto;
import com.example.bankaccount.model.Account;
import com.example.bankaccount.model.CheckingAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test de la classe {@link AccountMapper}
 */
class AccountMapperTest {

    /**
     * Test de la méthode toDto de AccountMapper
     */
    @Test
    void testAccountMapperToDto() {
        Account account = new CheckingAccount();
        account.deposit(200.0);

        AccountMapper mapper = new AccountMapper();
        AccountDto dto = mapper.toDto(account);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(account.getNumber(), dto.number());
        Assertions.assertEquals(account.getBalance(), dto.balance());
        Assertions.assertEquals("CHECKING", dto.type());
    }
}
