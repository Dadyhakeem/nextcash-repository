package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.enums.TransactionType;
import com.dev.hakeem.nextcash.service.TransactionService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.ExpensaMapper;
import com.dev.hakeem.nextcash.web.mapper.TransactionMapper;
import com.dev.hakeem.nextcash.web.request.TransactionRequest;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
import com.dev.hakeem.nextcash.web.response.ExpensaResponse;
import com.dev.hakeem.nextcash.web.response.TransactionResponse;
import com.dev.hakeem.nextcash.web.response.TransferencaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.dev.hakeem.nextcash.enums.TransactionType.DESPESA;

@Tag(name = "Transaction",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma Transaction.")
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;
    private final ExpensaMapper mapper;
    private final TransactionMapper transactionMapper;


    public TransactionController(TransactionService service, ExpensaMapper mapper, TransactionMapper transactionMapper) {
        this.service = service;
        this.mapper = mapper;

        this.transactionMapper = transactionMapper;
    }


    @Operation(summary = "Criar Transaction",description = "criar Transaction",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transaction criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })




    @GetMapping
    public ResponseEntity<List<?>> getTransacoes(@RequestParam(required = false) String tipo) {
        List<Transaction> transactions = service.obterTodosasTransferencias();

        // Mapeia todas as transações para suas respectivas respostas
        List<TransactionResponse> transacoesResponse = transactions.stream()
                .map(transactionMapper::mapToResponse)
                .collect(Collectors.toList());

        // Filtra por tipo, se fornecido
        if (tipo != null) {
            transacoesResponse = transacoesResponse.stream()
                    .filter(t -> t.getTipo().name().equalsIgnoreCase(tipo))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(transacoesResponse);
    }
}



