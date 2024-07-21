package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Goal;
import com.dev.hakeem.nextcash.service.GoalService;
import com.dev.hakeem.nextcash.web.mapper.GoalMapper;
import com.dev.hakeem.nextcash.web.request.GoalRequest;
import com.dev.hakeem.nextcash.web.response.GoalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<GoalResponse> createGoal(@RequestBody GoalRequest request){
        Goal goal = service.createGoal(request);
        GoalResponse response = mapper.toGoalResponse(goal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
