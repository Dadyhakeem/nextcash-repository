package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Expense;
import com.dev.hakeem.nextcash.service.ExpensaService;
import com.dev.hakeem.nextcash.web.mapper.ExpensaMapper;
import com.dev.hakeem.nextcash.web.request.ExpenseRequest;
import com.dev.hakeem.nextcash.web.response.ExpensaResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private  final ExpensaService service;
    private final ExpensaMapper mapper;
    @Autowired
    public ExpenseController(ExpensaService service, ExpensaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @PostMapping
    public ResponseEntity<ExpensaResponse> createIncome(@Valid @RequestBody ExpenseRequest request){
        Expense expense = service.createExpense(request);
        ExpensaResponse response = mapper.toResponse(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<ExpensaResponse>> listarTodos(){
        List<Expense> expenses = service.listarTodos();
        List<ExpensaResponse> response = expenses.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        service.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/{id}")
    public  ResponseEntity<ExpensaResponse>buscarPorId(@PathVariable Long id){
        Expense expense = service.buscarPorId(id);
        ExpensaResponse response = mapper.toResponse(expense);
        return  ResponseEntity.ok().body(response);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<ExpensaResponse> editarIncome(@Valid @PathVariable Long id, @RequestBody ExpenseRequest request){
        request.setId(id);
        Expense expense = service.editarIncome(request);
        ExpensaResponse response = mapper.toResponse(expense);
        return ResponseEntity.ok().body(response);
    }
}
