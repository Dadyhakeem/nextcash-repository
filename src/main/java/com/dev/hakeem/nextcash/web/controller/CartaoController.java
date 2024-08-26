package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Cartao;
import com.dev.hakeem.nextcash.service.CartaoService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.CartaoMapper;
import com.dev.hakeem.nextcash.web.request.CartaoRequest;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
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
@Tag(name = "Cartao",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma cartao.")
@RestController
@RequestMapping("/api/v1/cartoes")
public class CartaoController {

    private  final CartaoService service;
    private  final CartaoMapper mapper;

    @Autowired
    public CartaoController(CartaoService service, CartaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Criar Cartao",description = "criar Cartao",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cartao criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartaoResponse.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartaoResponse> createCartao(@Valid @RequestBody CartaoRequest request){
        Cartao cartao = service.createCartao(request);
        CartaoResponse response = mapper.toResponse(cartao);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Listar todos os cartoes",description = "Recurso pra listar todos os cartoes",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Todas os cartoes criados",
                            content = @Content(mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = CartaoResponse.class)))),
            })
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<CartaoResponse>> listarTodos(){
        List<Cartao> cartaos = service.listarTodos();
        List<CartaoResponse> responses = cartaos.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }


    @Operation(summary = "Recuperar cartao pelo id",description = "Recuperar cartao pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Cartao recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = CartaoResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Cartao nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')")
    private ResponseEntity<CartaoResponse> buscarPorId(@Valid @PathVariable Long id){
        Cartao cartao = service.buscarPorId(id);
        CartaoResponse response = mapper.toResponse(cartao);
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Deletar cartao pelo id",description = "deletar cartao pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Cartao deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Cartao nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole( 'CLIENT')")
    public  ResponseEntity<Void> deletarPorId(@Valid @PathVariable Long id){
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar cartao", description = " atualizar cartao",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Cartao atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = CartaoResponse.class))),
                    @ApiResponse(responseCode = "400",description = "Usuario e cartao  nao confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Cartao nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartaoResponse> atualizar(@Valid @RequestBody CartaoRequest request,@PathVariable Long id){
        Cartao cartao = service.atualizarCartao(id,request);
        CartaoResponse response = mapper.toResponse(cartao);
        return  ResponseEntity.ok(response);
    }


}
