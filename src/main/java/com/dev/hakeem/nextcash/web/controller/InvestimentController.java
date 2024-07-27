package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Investiment;
import com.dev.hakeem.nextcash.service.InvestmentService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.InvestimentMapper;
import com.dev.hakeem.nextcash.web.request.InvestmentRequest;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
import com.dev.hakeem.nextcash.web.response.InvestmentResponse;
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
@Tag(name = "Investimento",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma Investimento.")
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


    @Operation(summary = "Criar investimento",description = "criar investimento",
            responses = {
                    @ApiResponse(responseCode = "201", description = "investimento criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvestmentResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PostMapping
    public ResponseEntity<InvestmentResponse> createInvestment(@Valid @RequestBody InvestmentRequest request){
        Investiment investiment = service.createInvestment(request);
        InvestmentResponse response = mapper.toResponse(investiment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Atualizar invetimento", description = " atualizar invetimento",
            responses = {
                    @ApiResponse(responseCode = "200",description = "invetimento atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = InvestmentResponse.class))),
                    @ApiResponse(responseCode = "400",description = "Usuario e invetimento  nao confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "404", description = "invetimento nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<InvestmentResponse>atualizar(@PathVariable Long id,@RequestBody InvestmentRequest request){
        request.setId(id);
         Investiment investiment = service.atualizar(request);
         InvestmentResponse response =  mapper.toResponse(investiment);
         return ResponseEntity.ok(response);
    }


    @Operation(summary = "Listar todos os invetimentos",description = "Recurso pra listar todos os invetimentos",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Todas os invetimentos criados",
                            content = @Content(mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = InvestmentResponse.class)))),
            })
    @GetMapping
    public ResponseEntity<List<InvestmentResponse>> listarTodos(){
         List<Investiment> investiments = service.listarTodos();
         List<InvestmentResponse> responses = investiments.stream().map(mapper::toResponse).collect(Collectors.toList());
         return ResponseEntity.ok().body(responses);
    }


    @Operation(summary = "Recuperar invetimento pelo id",description = "Recuperar invetimento pelo id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "invetimento recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = InvestmentResponse.class))),
                    @ApiResponse(responseCode = "404",description = "invetimento nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<InvestmentResponse>BuscarPorId(@PathVariable Long id){
         Investiment investiment = service.busrcarPorId(id);
         InvestmentResponse response = mapper.toResponse(investiment);
         return ResponseEntity.ok(response);
    }


    @Operation(summary = "Deletar invetimento pelo id",description = "deletar invetimento pelo id",
            responses = {
                    @ApiResponse(responseCode = "204",description = "invetimento deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "invetimento nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
         service.deletarPorId(id);
         return ResponseEntity.noContent().build();
    }


}
