package com.example.bankaccount.service;


import com.example.bankaccount.dto.account.AccountDto;
import com.example.bankaccount.dto.account.CreateAccountDto;
import com.example.bankaccount.dto.operation.AddOperationDto;
import com.example.bankaccount.dto.accountStatement.AccountStatementDto;
import com.example.bankaccount.mapper.AccountMapper;
import com.example.bankaccount.model.Account;
import com.example.bankaccount.model.CheckingAccount;
import com.example.bankaccount.model.Operation;
import com.example.bankaccount.model.OperationType;
import com.example.bankaccount.repository.AccountRepository;
import com.example.bankaccount.repository.OperationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Tests unitaires pour le service {@link AccountService}
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    /*
     * Test de la création d’un compte courant
     */
    @Test
    void testCreateCheckingAccount() {
        CreateAccountDto createAccountDto = new CreateAccountDto("CHECKING");
        CheckingAccount account = new CheckingAccount();
        account.setNumber("12345");
        account.setBalance(100.0);

        Account saved = new CheckingAccount();
        saved.setId(1L);
        saved.setNumber("12345");
        saved.setBalance(100.0);

        AccountDto expectedDto = new AccountDto("12345", 100.0, "CHECKING");

        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(saved);
        Mockito.when(accountMapper.toDto(saved)).thenReturn(expectedDto);

        AccountDto result = accountService.create(createAccountDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedDto, result);
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(accountMapper, Mockito.times(1)).toDto(saved);
    }

    /*
     * Test de la création d’un compte avec un type invalide
     */
    @Test
    void testCreateInvalidAccountType() {
        CreateAccountDto createAccountDto = new CreateAccountDto("INVALID_TYPE");

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.create(createAccountDto);
        });

        Assertions.assertEquals("Invalid account type: INVALID_TYPE", exception.getMessage());
        Mockito.verify(accountRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(accountMapper, Mockito.never()).toDto(Mockito.any());
    }

    /*
     * Test du retrait d’argent sur un compte
     */
    @Test
    void testWithdraw() {
        AddOperationDto operationDto = new AddOperationDto("12345", 50.0);
        CheckingAccount account = new CheckingAccount();
        account.setNumber("12345");
        account.setBalance(200.0);

        AccountDto expectedDto = new AccountDto("12345", 150.0, "CHECKING");

        Mockito.when(accountRepository.findByNumber("12345")).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.toDto(account)).thenReturn(expectedDto);

        AccountDto result = accountService.withdraw(operationDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedDto, result);
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(accountMapper, Mockito.times(1)).toDto(account);
    }

    /*
     * Test du retrait d’argent sur un compte inexistant
     */
    @Test
    void testWithdraw_AccountNotFound() {
        AddOperationDto operationDto = new AddOperationDto("99999", 50.0);

        Mockito.when(accountRepository.findByNumber("99999")).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            accountService.withdraw(operationDto);
        });

        Assertions.assertEquals("Account not found: 99999", exception.getMessage());
    }

    /*
     * Test de la récupération du relevé mensuel d’un compte
     */
    @Test
    void testGetMonthlyStatement() {
        CheckingAccount account = new CheckingAccount();
        account.setNumber("12345");
        account.setBalance(500.0);

        Operation op1 = new Operation();
        op1.setId(1L);
        op1.setAmount(100.0);
        op1.setType(OperationType.DEPOSIT);
        op1.setDate(LocalDateTime.now());
        op1.setAccount(account);

        Operation op2 = new Operation();
        op2.setId(2L);
        op2.setAmount(50.0);
        op2.setType(OperationType.WITHDRAW);
        op2.setDate(LocalDateTime.now().minusDays(10));
        op2.setAccount(account);

        Mockito.when(accountRepository.findByNumber("12345")).thenReturn(Optional.of(account));
        Mockito.when(operationRepository.findByAccount_NumberAndDateAfterOrderByDateDesc(
                Mockito.eq("12345"),
                Mockito.any(LocalDateTime.class)))
                .thenReturn(List.of(op1, op2));

        AccountStatementDto result = accountService.getMonthlyStatement("12345");

        Assertions.assertEquals("12345", result.accountNumber());
        Assertions.assertEquals("CHECKING", result.accountType());
        Assertions.assertEquals(500.0, result.balance());
        Assertions.assertEquals(2, result.operations().size());
    }
}
