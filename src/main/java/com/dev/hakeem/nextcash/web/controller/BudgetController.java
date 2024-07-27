package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Budget;
import com.dev.hakeem.nextcash.service.BudgetService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.BudgetMapper;
import com.dev.hakeem.nextcash.web.request.BudgetRequest;
import com.dev.hakeem.nextcash.web.response.BudgetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@Tag(name = "Budget",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de um budget.")
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


    @Operation(summary = "Criar Budget",description = "criar Budget",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Budget criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BudgetResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(@RequestBody BudgetRequest request){
        Budget budget = service.createBudget(request);
        BudgetResponse response = mapper.toResponse(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Listar todos as budget",description = "Recurso pra listar todos as budget",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Todas as budget criados",
                            content = @Content(mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = BudgetResponse.class)))),
            })
    @GetMapping
    public ResponseEntity<List<BudgetResponse>> listarTodas(){
        List<Budget> budgets = service.listarTodas();
        List<BudgetResponse> responses = budgets.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }


    @Operation(summary = "Atualizar budget", description = " atualizar budget",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Budget atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = BudgetResponse.class))),
                    @ApiResponse(responseCode = "400",description = "Usuario e budget  nao confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Budget nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse>atualizar(@Valid @PathVariable Long id, @RequestBody BudgetRequest request){
        request.setId(id);
        Budget budget = service.atualizar(request);
        BudgetResponse response = mapper.toResponse(budget);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Deletar budget pelo id",description = "deletar budget pelo id",
            responses = {
                    @ApiResponse(responseCode = "204",description = "budget deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "budget nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeloId(@Valid @PathVariable Long id){
        service.deletaPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recuperar budget pelo id",description = "Recuperar budget pelo id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Budget recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = BudgetResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Budget nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> buscarPeloId(@Valid @PathVariable Long id){
        Budget budget = service.buscarPeloId(id);
        BudgetResponse response = mapper.toResponse(budget);
        return ResponseEntity.ok(response);
    }


}
