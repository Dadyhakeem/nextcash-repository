package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.service.AccountService;
import com.dev.hakeem.nextcash.web.mapper.ToAccountMapper;
import com.dev.hakeem.nextcash.web.request.AccountRequest;
import com.dev.hakeem.nextcash.web.response.AccountResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService service;
    private final ToAccountMapper toAccountMapper;

    @Autowired
    public AccountController(AccountService service, ToAccountMapper toAccountMapper) {
        this.service = service;
        this.toAccountMapper = toAccountMapper;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        Account account = service.createAccount(request);
        AccountResponse response = toAccountMapper.toReponse(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        Account account = service.buscarPorId(id);
        AccountResponse response = toAccountMapper.toReponse(account);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> listAllAccounts() {
        List<Account> accounts = service.listarTodos();
        List<AccountResponse> responseList = accounts.stream()
                .map(toAccountMapper::toReponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
