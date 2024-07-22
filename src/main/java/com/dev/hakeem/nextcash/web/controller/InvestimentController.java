package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Investiment;
import com.dev.hakeem.nextcash.service.InvestmentService;
import com.dev.hakeem.nextcash.web.mapper.InvestimentMapper;
import com.dev.hakeem.nextcash.web.request.InvestmentRequest;
import com.dev.hakeem.nextcash.web.response.InvestmentResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/investimentos")
public class InvestimentController {

    private  final InvestmentService service;
    private final InvestimentMapper mapper;
     @Autowired
    public InvestimentController(InvestmentService service, InvestimentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @PostMapping
    public ResponseEntity<InvestmentResponse> createInvestment(@Valid @RequestBody InvestmentRequest request){
        Investiment investiment = service.createInvestment(request);
        InvestmentResponse response = mapper.toResponse(investiment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<InvestmentResponse>atualizar(@PathVariable Long id,@RequestBody InvestmentRequest request){
        request.setId(id);
         Investiment investiment = service.atualizar(request);
         InvestmentResponse response =  mapper.toResponse(investiment);
         return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<InvestmentResponse>> listarTodos(){
         List<Investiment> investiments = service.listarTodos();
         List<InvestmentResponse> responses = investiments.stream().map(mapper::toResponse).collect(Collectors.toList());
         return ResponseEntity.ok().body(responses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<InvestmentResponse>BuscarPorId(@PathVariable Long id){
         Investiment investiment = service.busrcarPorId(id);
         InvestmentResponse response = mapper.toResponse(investiment);
         return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
         service.deletarPorId(id);
         return ResponseEntity.noContent().build();
    }


}
