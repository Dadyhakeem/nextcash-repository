package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.service.TransactionService;
import com.dev.hakeem.nextcash.web.mapper.TransactionMapper;
import com.dev.hakeem.nextcash.web.request.TransactionRequest;
import com.dev.hakeem.nextcash.web.response.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;
    private final TransactionMapper mapper;

    public TransactionController(TransactionService service, TransactionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request){
        Transaction transaction = service.createTransaction(request);
        TransactionResponse response = mapper.ToResponse(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public  ResponseEntity<List<TransactionResponse>> listarTodos(){
        List<Transaction> transaction = service.listarTodos();
        List<TransactionResponse> responses = transaction.stream().map(mapper::ToResponse).collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deletar(@Valid @PathVariable Long id){
         service.deletar(id);
         return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse>buscarPorId(@Valid @PathVariable Long id){
        Transaction transaction = service.buscarPorid(id);
        TransactionResponse response = mapper.ToResponse(transaction);
        return ResponseEntity.ok(response);
    }


}
