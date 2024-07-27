package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Category;
import com.dev.hakeem.nextcash.service.CategoryService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.CategoryMapper;
import com.dev.hakeem.nextcash.web.request.CategoryRequest;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
import com.dev.hakeem.nextcash.web.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Tag(name = "Category",description = "Contem todos as operacoes relativas aos recursos para criar , edition e leitura de uma Category.")
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private  final CategoryService service;
    private  final CategoryMapper mapper;

    public CategoryController(CategoryService service, CategoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @Operation(summary = "Criar Category",description = "criar Category",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Category criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
     @PostMapping
     public ResponseEntity<CategoryResponse> createCatecory(@Valid @RequestBody CategoryRequest request){
         Category category = service.createCategory(request);
         CategoryResponse response = mapper.toResponse(category);
         return ResponseEntity.ok().body(response);
     }


    @Operation(summary = "Atualizar Category", description = " atualizar Category",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Category atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = CategoryResponse.class))),
                    @ApiResponse(responseCode = "400",description = "Usuario e Category  nao confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Category nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
     @PutMapping("/{id}")
     public ResponseEntity<CategoryResponse> atualizarCategory(@Valid @PathVariable Long id,@RequestBody CategoryRequest request){
         request.setId(id);
        Category category = service.atualizar(request);
        CategoryResponse response = mapper.toResponse(category);
        return ResponseEntity.ok().body(response);
     }


    @Operation(summary = "Recuperar Category pelo id",description = "Recuperar Category pelo id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Category recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = CategoryResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Category nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
     @GetMapping("/{id}")
     public ResponseEntity<CategoryResponse>BuscarPorId(@Valid @PathVariable Long id){
         Category category = service.buscarPorId(id);
         CategoryResponse response = mapper.toResponse(category);
         return ResponseEntity.ok().body(response);
     }


    @Operation(summary = "Listar todos os Category",description = "Recurso pra listar todos os Category",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Todas os Category criados",
                            content = @Content(mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))),
            })
     @GetMapping
     public ResponseEntity<List<CategoryResponse>> listarTodos(){
        List<Category> categories = service.listarTodos();
        List<CategoryResponse> responses = categories.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
     }


    @Operation(summary = "Deletar Category pelo id",description = "deletar Category pelo id",
            responses = {
                    @ApiResponse(responseCode = "204",description = "Category deletada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Category nao encontrada",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class)))
            })
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deletarPorId(@Valid @PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
     }
}
