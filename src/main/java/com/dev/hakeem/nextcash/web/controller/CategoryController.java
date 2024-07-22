package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Category;
import com.dev.hakeem.nextcash.service.CategoryService;
import com.dev.hakeem.nextcash.web.mapper.CategoryMapper;
import com.dev.hakeem.nextcash.web.request.CategoryRequest;
import com.dev.hakeem.nextcash.web.response.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private  final CategoryService service;
    private  final CategoryMapper mapper;

    public CategoryController(CategoryService service, CategoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
     @PostMapping
     public ResponseEntity<CategoryResponse> createCatecory(@Valid @RequestBody CategoryRequest request){
         Category category = service.createCategory(request);
         CategoryResponse response = mapper.toResponse(category);
         return ResponseEntity.ok().body(response);
     }
     @PutMapping("/{id}")
     public ResponseEntity<CategoryResponse> atualizarCategory(@Valid @PathVariable Long id,@RequestBody CategoryRequest request){
         request.setId(id);
        Category category = service.atualizar(request);
        CategoryResponse response = mapper.toResponse(category);
        return ResponseEntity.ok().body(response);
     }
     @GetMapping("/{id}")
     public ResponseEntity<CategoryResponse>BuscarPorId(@Valid @PathVariable Long id){
         Category category = service.buscarPorId(id);
         CategoryResponse response = mapper.toResponse(category);
         return ResponseEntity.ok().body(response);
     }
     @GetMapping
     public ResponseEntity<List<CategoryResponse>> listarTodos(){
        List<Category> categories = service.listarTodos();
        List<CategoryResponse> responses = categories.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
     }
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deletarPorId(@Valid @PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
     }
}
