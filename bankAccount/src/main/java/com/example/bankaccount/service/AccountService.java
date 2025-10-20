package com.example.bankaccount.service;

import com.example.bankaccount.dto.account.AccountDto;
import com.example.bankaccount.dto.account.CreateAccountDto;
import com.example.bankaccount.dto.operation.AddOperationDto;
import com.example.bankaccount.dto.accountStatement.AccountStatementDto;
import com.example.bankaccount.dto.operation.OperationDto;
import com.example.bankaccount.mapper.AccountMapper;
import com.example.bankaccount.model.Account;
import com.example.bankaccount.model.CheckingAccount;
import com.example.bankaccount.model.Operation;
import com.example.bankaccount.model.OperationType;
import com.example.bankaccount.model.SavingsAccount;
import com.example.bankaccount.repository.AccountRepository;
import com.example.bankaccount.repository.OperationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Service de Compte
 */
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final AccountMapper accountMapper;

    /**
     * Création d’un compte courant ou épargne
     */
    public AccountDto create(final CreateAccountDto createAccountDto) {
        Account account = switch (createAccountDto.type().toUpperCase()) {
            case "CHECKING" -> new CheckingAccount();
            case "SAVINGS" -> new SavingsAccount();
            default -> throw new IllegalArgumentException("Invalid account type: " + createAccountDto.type());
        };
        account.setNumber(generateAccountNumber());
        account.setBalance(100.0);
        Account saved = accountRepository.save(account);

        return accountMapper.toDto(saved);
    }

    /**
     * Récupération de tous les comptes
     */
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .toList();
    }

    /**
     * Dépôt d’argent sur un compte
     * @param operationDto  opération contenant le numéro de compte et le montant
     * @return l'objet AccountDto avec les informations mises à jour
     */
    public AccountDto deposit(final AddOperationDto operationDto) {
        return operate(operationDto, OperationType.DEPOSIT);
    }

    /**
     * Dépôt d’argent sur un compte
     * @param operationDto  opération contenant le numéro de compte et le montant
     * @return l'objet AccountDto avec les informations mises à jour
     */
    public AccountDto withdraw(final AddOperationDto operationDto) {
        return operate(operationDto, OperationType.WITHDRAW);
    }

    /**
     * Construit l'objet de relevé de compte sur le mois glissant
     * @param accountNumber numéro de compte
     * @return  Relevé de compte avec la liste des opérations
     */
    public AccountStatementDto getMonthlyStatement(final String accountNumber) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + accountNumber));

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Operation> operations = operationRepository
                .findByAccount_NumberAndDateAfterOrderByDateDesc(accountNumber, oneMonthAgo);

        List<OperationDto> lines = operations.stream()
                .map(operation -> new OperationDto(
                        operation.getId(),
                        operation.getAmount(),
                        operation.getType(),
                        operation.getDate()
                )).toList();

        String type = (account instanceof CheckingAccount) ? "CHECKING" : "SAVINGS";

        return new AccountStatementDto(account.getNumber(), type, account.getBalance(), lines);
    }

    /**
     * Fonction de gestion de l'opération (dépot/retrait)
     * @param dto détail de l'opération
     * @param type  type de l'opération
     * @return Compte mis à jour
     */
    public AccountDto operate(final AddOperationDto dto, final OperationType type) {
        Account account = accountRepository.findByNumber(dto.accountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + dto.accountNumber()));

        switch (type) {
            case DEPOSIT -> account.deposit(dto.amount());
            case WITHDRAW -> account.withdraw(dto.amount());
            default -> throw new IllegalArgumentException("Unknown operation type");
        }

        accountRepository.save(account);

        Operation operation = new Operation();
        operation.setAmount(dto.amount());
        operation.setType(type);
        operation.setDate(LocalDateTime.now());
        operation.setAccount(account);
        operationRepository.save(operation);

        return accountMapper.toDto(account);
    }

    /**
     * Génère un numéro de compte aléatoire de 5 chiffres
     * @return numéro de compte en String
     */
    private String generateAccountNumber() {
        Random random = new Random();
        int number = 10000 + random.nextInt(90000);
        return String.valueOf(number);
    }
}
