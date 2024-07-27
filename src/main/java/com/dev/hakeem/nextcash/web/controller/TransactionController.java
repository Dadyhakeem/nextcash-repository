package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.service.TransactionService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.TransactionMapper;
import com.dev.hakeem.nextcash.web.request.TransactionRequest;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
import com.dev.hakeem.nextcash.web.response.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@Tag(name = "Transaction",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma Transaction.")
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;
    private final TransactionMapper mapper;

    public TransactionController(TransactionService service, TransactionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @Operation(summary = "Criar Transaction",description = "criar Transaction",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transaction criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request){
        Transaction transaction = service.createTransaction(request);
        TransactionResponse response = mapper.ToResponse(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Listar todos os Transaction",description = "Recurso pra listar todos os Transaction",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Todas os Transaction criados",
                            content = @Content(mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = CartaoResponse.class)))),
            })
    @GetMapping
    public  ResponseEntity<List<TransactionResponse>> listarTodos(){
        List<Transaction> transaction = service.listarTodos();
        List<TransactionResponse> responses = transaction.stream().map(mapper::ToResponse).collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }


    @Operation(summary = "Deletar Transaction pelo id",description = "deletar Transaction pelo id",
            responses = {
                    @ApiResponse(responseCode = "204",description = "Transaction deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Transaction nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deletar(@Valid @PathVariable Long id){
         service.deletar(id);
         return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Recuperar Transaction pelo id",description = "Recuperar Transaction pelo id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Transaction recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = TransactionResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Transaction nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse>buscarPorId(@Valid @PathVariable Long id){
        Transaction transaction = service.buscarPorid(id);
        TransactionResponse response = mapper.ToResponse(transaction);
        return ResponseEntity.ok(response);
    }


}
