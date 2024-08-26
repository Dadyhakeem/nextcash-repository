package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Income;
import com.dev.hakeem.nextcash.service.IncomeService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.IncomeMapper;
import com.dev.hakeem.nextcash.web.request.IncomeRequest;
import com.dev.hakeem.nextcash.web.response.IncomeResponse;
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
@Tag(name = "Income",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma Income.")
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


    @Operation(summary = "Criar Income",description = "criar Income",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Income criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = IncomeResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<IncomeResponse> createIncome(@Valid @RequestBody IncomeRequest request){
        Income income = service.createIncome(request);
        IncomeResponse response = mapper.toResponse(income);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Listar todos os Income",description = "Recurso pra listar todos os Income",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Todas os Income criados",
                            content = @Content(mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = IncomeResponse.class)))),
            })
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<IncomeResponse>> listarTodos(){
        List<Income> income = service.listarTodos();
        List<IncomeResponse> response = income.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar Icome pelo id",description = "deletar Income pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Income deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Income nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public  ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        service.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "Recuperar Income pelo id",description = "Recuperar income pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Income recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = IncomeResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Income nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public  ResponseEntity<IncomeResponse>buscarPorId(@PathVariable Long id){
        Income income = service.buscarPorId(id);
        IncomeResponse response = mapper.toResponse(income);
        return  ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Atualizar Income", description = " atualizar income",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Income atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = IncomeResponse.class))),
                    @ApiResponse(responseCode = "400",description = "Usuario , income, conta e transation  nao confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Income nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public  ResponseEntity<IncomeResponse> editarIncome(@Valid @PathVariable Long id, @RequestBody IncomeRequest request){
        request.setId(id);
        Income income = service.editarIncome(request);
        IncomeResponse response = mapper.toResponse(income);
        return ResponseEntity.ok().body(response);
    }
}
