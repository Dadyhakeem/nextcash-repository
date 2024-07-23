package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Budget;
import com.dev.hakeem.nextcash.service.BudgetService;
import com.dev.hakeem.nextcash.web.mapper.BudgetMapper;
import com.dev.hakeem.nextcash.web.request.BudgetRequest;
import com.dev.hakeem.nextcash.web.response.BudgetResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {

    private final BudgetService service;
    private  final BudgetMapper mapper;
    @Autowired
    public BudgetController(BudgetService service, BudgetMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(@RequestBody BudgetRequest request){
        Budget budget = service.createBudget(request);
        BudgetResponse response = mapper.toResponse(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<BudgetResponse>> listarTodas(){
        List<Budget> budgets = service.listarTodas();
        List<BudgetResponse> responses = budgets.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse>atualizar(@Valid @PathVariable Long id, @RequestBody BudgetRequest request){
        request.setId(id);
        Budget budget = service.atualizar(request);
        BudgetResponse response = mapper.toResponse(budget);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeloId(@Valid @PathVariable Long id){
        service.deletaPeloId(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> buscarPeloId(@Valid @PathVariable Long id){
        Budget budget = service.buscarPeloId(id);
        BudgetResponse response = mapper.toResponse(budget);
        return ResponseEntity.ok(response);
    }


}
