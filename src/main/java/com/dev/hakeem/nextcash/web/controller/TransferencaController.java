package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Transferenca;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.service.TransferencaService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.TransfeencaMapper;
import com.dev.hakeem.nextcash.web.request.TransferencaRequest;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
import com.dev.hakeem.nextcash.web.response.TransferencaResponse;
import io.swagger.v3.oas.annotations.Operation;
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


@Tag(name = "Transferencia",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma Transferencia.")
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


    @Operation(summary = "Criar Transferenca",description = "criar Transferenca",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transferenca criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransferencaResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PostMapping("/realizar")
    @PreAuthorize("hasRole('CLIENT')")
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


    @Operation(summary = "Recuperar Transferenca pelo id",description = "Recuperar Transferenca pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Transferenca recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = TransferencaResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Transferenca nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
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


    @Operation(summary = "Deletar Transferenca pelo id",description = "deletar Transferenca pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Transferenca deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Transferenca nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public ResponseEntity<String> deletarTransferencia(@Valid @PathVariable Long id) {
        try {
            transferencaService.deletPorId(id);
            return ResponseEntity.ok("Transferência deletada com sucesso!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
