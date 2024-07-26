package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Goal;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.repository.GoalRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.GoalRequest;
import com.dev.hakeem.nextcash.web.response.GoalResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class GoalMapper {

    private final GoalRepository repository;
    private final UserRepository userRepository;

    public GoalMapper(GoalRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Goal toGoalrequeste(GoalRequest request){
        Optional<User> user = userRepository.findById(request.getUserid());

        Goal goal = new Goal();
        goal.setId(request.getId());
        goal.setName(request.getName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount());
        goal.setDeadline(request.getDeadline());
        goal.setUserid(user.get());

        return goal;
    }


    public GoalResponse toGoalResponse(Goal goal) {
        GoalResponse response = new GoalResponse();
        response.setId(goal.getId());
        response.setName(goal.getName());
        response.setTargetAmount(goal.getTargetAmount());
        response.setCurrentAmount(goal.getCurrentAmount());
        response.setDeadline(goal.getDeadline());
          // Associando o usuário corretamente
        return response;
    }


}
