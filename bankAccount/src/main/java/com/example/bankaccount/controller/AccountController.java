package com.example.bankaccount.controller;

import com.example.bankaccount.dto.account.AccountDto;
import com.example.bankaccount.dto.account.CreateAccountDto;
import com.example.bankaccount.dto.operation.AddOperationDto;
import com.example.bankaccount.dto.accountStatement.AccountStatementDto;
import com.example.bankaccount.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody final CreateAccountDto account) {
        return ResponseEntity.ok(accountService.create(account));
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDto> withdraw(@RequestBody final AddOperationDto dto) {
        return ResponseEntity.ok(accountService.withdraw(dto));
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestBody final AddOperationDto dto) {
        return ResponseEntity.ok(accountService.deposit(dto));
    }

    @GetMapping("/{number}/statement")
    public ResponseEntity<AccountStatementDto> getStatement(@PathVariable String number) {
        return ResponseEntity.ok(accountService.getMonthlyStatement(number));
    }
}
