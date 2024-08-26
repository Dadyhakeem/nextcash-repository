package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Expense;
import com.dev.hakeem.nextcash.service.ExpensaService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.ExpensaMapper;
import com.dev.hakeem.nextcash.web.request.ExpenseRequest;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
import com.dev.hakeem.nextcash.web.response.ExpensaResponse;
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
@Tag(name = "Expense",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma Expense.")
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

    @Operation(summary = "Criar Expense",description = "criar Expense",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Expense criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpensaResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ExpensaResponse> createExpense(@Valid @RequestBody ExpenseRequest request){
        Expense expense = service.createExpense(request);
        ExpensaResponse response = mapper.toResponse(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Listar todos os Expenses",description = "Recurso pra listar todos os Expenses",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Todas os Expenses criados",
                            content = @Content(mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = ExpensaResponse.class)))),
            })
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ExpensaResponse>> listarTodos(){
        List<Expense> expenses = service.listarTodos();
        List<ExpensaResponse> response = expenses.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Deletar Expense pelo id",description = "deletar expense pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Expense deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Expense nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public  ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        service.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "Recuperar Expense pelo id",description = "Recuperar Expense pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Expense recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ExpensaResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Expense nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public  ResponseEntity<ExpensaResponse>buscarPorId(@PathVariable Long id){
        Expense expense = service.buscarPorId(id);
        ExpensaResponse response = mapper.toResponse(expense);
        return  ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Atualizar Expense", description = " atualizar Expense",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Expense atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ExpensaResponse.class))),
                    @ApiResponse(responseCode = "400",description = "Expense e conta e transaction  nao confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Expense nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public  ResponseEntity<ExpensaResponse> editarExpense(@Valid @PathVariable Long id, @RequestBody ExpenseRequest request){
        request.setId(id);
        Expense expense = service.editarIncome(request);
        ExpensaResponse response = mapper.toResponse(expense);
        return ResponseEntity.ok().body(response);
    }
}
