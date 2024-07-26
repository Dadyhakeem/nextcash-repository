package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Income;
import com.dev.hakeem.nextcash.service.IncomeService;
import com.dev.hakeem.nextcash.web.mapper.IncomeMapper;
import com.dev.hakeem.nextcash.web.request.IncomeRequest;
import com.dev.hakeem.nextcash.web.response.IncomeResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/incomes")
public class IncomeController {

    private  final IncomeService service;
    private final IncomeMapper mapper;
    @Autowired
    public IncomeController(IncomeService service, IncomeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @PostMapping
    public ResponseEntity<IncomeResponse> createIncome(@Valid @RequestBody IncomeRequest request){
        Income income = service.createIncome(request);
        IncomeResponse response = mapper.toResponse(income);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<IncomeResponse>> listarTodos(){
        List<Income> income = service.listarTodos();
        List<IncomeResponse> response = income.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        service.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/{id}")
    public  ResponseEntity<IncomeResponse>buscarPorId(@PathVariable Long id){
        Income income = service.buscarPorId(id);
        IncomeResponse response = mapper.toResponse(income);
        return  ResponseEntity.ok().body(response);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<IncomeResponse> editarIncome(@Valid @PathVariable Long id, @RequestBody IncomeRequest request){
        request.setId(id);
        Income income = service.editarIncome(request);
        IncomeResponse response = mapper.toResponse(income);
        return ResponseEntity.ok().body(response);
    }
}
