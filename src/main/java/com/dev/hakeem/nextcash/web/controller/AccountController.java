package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.service.AccountService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.ToAccountMapper;
import com.dev.hakeem.nextcash.web.request.AccountRequest;
import com.dev.hakeem.nextcash.web.response.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;
@Tag(name = "Acount",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma conta.")
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

    @Operation(summary = "Criar conta",description = "criar conta",
         responses = {
            @ApiResponse(responseCode = "201",description = "Conta criada com sucesso",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "400",description = "Recursos nao processado por dados de entrada  invalidos",
                         content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))

    })
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        Account account = service.createAccount(request);
        AccountResponse response = toAccountMapper.toReponse(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Recuperar conta pelo id",description = "Recuperar conta pelo id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Conta reuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = AccountResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Conta nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        Account account = service.buscarPorId(id);
        AccountResponse response = toAccountMapper.toReponse(account);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar todos as contas",description = "Recurso pra listar todos as contas",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Todas as contas cadastrada",
                            content = @Content(mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = AccountResponse.class)))),
            })
    @GetMapping
    public ResponseEntity<List<AccountResponse>> listAllAccounts() {
        List<Account> accounts = service.listarTodos();
        List<AccountResponse> responseList = accounts.stream()
                .map(toAccountMapper::toReponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Deletar conta pelo id",description = "deletar conta pelo id",
            responses = {
                    @ApiResponse(responseCode = "204",description = "Conta deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Conta nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
