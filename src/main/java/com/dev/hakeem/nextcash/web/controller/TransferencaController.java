package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Transferenca;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.service.TransferencaService;
import com.dev.hakeem.nextcash.web.mapper.TransfeencaMapper;
import com.dev.hakeem.nextcash.web.request.TransferencaRequest;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.response.TransferencaResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transferencias")
public class TransferencaController {

    private final TransferencaService transferencaService;
    private  final TransfeencaMapper mapper;

    @Autowired
    public TransferencaController(TransferencaService transferencaService, TransfeencaMapper mapper) {
        this.transferencaService = transferencaService;
        this.mapper = mapper;
    }

    @PostMapping("/realizar")
    public ResponseEntity<String> realizarTransferencia(@Valid @RequestBody TransferencaRequest transferencaRequest) {
        try {
            transferencaService.createTransfer(transferencaRequest);
            return ResponseEntity.ok("Transferência realizada com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (BusinessException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferencaResponse> obterTransferenciaPorId(@Valid @PathVariable Long id) {
        try {
            Transferenca transferenca = transferencaService.obterTranferPorId(id);
            TransferencaResponse response = mapper.toResponse(transferenca);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
             new EntityNotFoundException("Transferência não encontrada");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarTransferencia(@Valid @PathVariable Long id) {
        try {
            transferencaService.deletPorId(id);
            return ResponseEntity.ok("Transferência deletada com sucesso!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
