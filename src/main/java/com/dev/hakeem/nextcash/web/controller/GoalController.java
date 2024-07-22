package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Goal;
import com.dev.hakeem.nextcash.service.GoalService;
import com.dev.hakeem.nextcash.web.mapper.GoalMapper;
import com.dev.hakeem.nextcash.web.request.GoalRequest;
import com.dev.hakeem.nextcash.web.response.GoalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalController {

    private  final GoalService service;
    private final GoalMapper mapper;

    public GoalController(GoalService service, GoalMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @PostMapping
    public ResponseEntity<GoalResponse> createGoal(@Valid @RequestBody GoalRequest request){
        Goal goal = service.createGoal(request);
        GoalResponse response = mapper.toGoalResponse(goal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Goal> buscarPorId(@Valid @PathVariable Long id) {
        Optional<Goal> goal = service.buscarPorId(id);
        return goal.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<Goal> atualizar(@PathVariable Long id, @Valid @RequestBody GoalRequest request) {
        request.setId(id);
        Goal goal = service.atualizar(request);
        return ResponseEntity.ok().body(goal);
    }



    @GetMapping
    public  ResponseEntity<List<Goal>> listartodas(){
        List<Goal> goals = service.ListarTodos();
        return ResponseEntity.ok().body(goals);
    }

}
