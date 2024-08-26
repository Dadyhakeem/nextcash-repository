package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Goal;
import com.dev.hakeem.nextcash.service.GoalService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.GoalMapper;
import com.dev.hakeem.nextcash.web.request.GoalRequest;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
import com.dev.hakeem.nextcash.web.response.GoalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Tag(name = "Goal",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma Goal.")
@RestController
@RequestMapping("/api/v1/goals")
public class GoalController {

    private  final GoalService service;
    private final GoalMapper mapper;

    public GoalController(GoalService service, GoalMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @Operation(summary = "Criar Goal(meta)",description = "criar Goal",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Goal criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<GoalResponse> createGoal(@Valid @RequestBody GoalRequest request){
        Goal goal = service.createGoal(request);
        GoalResponse response = mapper.toGoalResponse(goal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Recuperar goal pelo id",description = "Recuperar goal pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Goal recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = GoalResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Goal nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public ResponseEntity<Goal> buscarPorId(@Valid @PathVariable Long id) {
        Optional<Goal> goal = service.buscarPorId(id);
        return goal.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }




    @Operation(summary = "Deletar goal pelo id",description = "deletar goal pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Goal deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Cartao nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Atualizar goal", description = " atualizar goal",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Goal atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = GoalResponse.class))),
                    @ApiResponse(responseCode = "400",description = "Usuario e goal  nao confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Goal nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')AND (#id == authentication.principal.id)")
    public ResponseEntity<Goal> atualizar(@PathVariable Long id, @Valid @RequestBody GoalRequest request) {
        request.setId(id);
        Goal goal = service.atualizar(request);
        return ResponseEntity.ok().body(goal);
    }


    @Operation(summary = "Listar todos os goal",description = "Recurso pra listar todos os goal",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Todas os goal criados",
                            content = @Content(mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = GoalResponse.class)))),
            })
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public  ResponseEntity<List<Goal>> listartodas(){
        List<Goal> goals = service.ListarTodos();
        return ResponseEntity.ok().body(goals);
    }

}
