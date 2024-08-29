package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Goal;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.repository.GoalRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.GoalRequest;
import com.dev.hakeem.nextcash.web.response.GoalResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        goal.setName(request.getName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount());
        try {
            LocalDate deadline = LocalDate.parse(request.getDeadline());
            goal.setDeadline(deadline);
        } catch (DateTimeParseException e) {
            // Lida com o erro de parsing de data
            throw new IllegalArgumentException("Formato de data inválido", e);
        }

        goal.setUserid(user.get());

        return goal;
    }


    public GoalResponse toGoalResponse(Goal goal) {
        GoalResponse response = new GoalResponse();
        response.setId(goal.getId());
        response.setName(goal.getName());
        response.setTargetAmount(goal.getTargetAmount());
        response.setCurrentAmount(goal.getCurrentAmount());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        response.setDeadline(LocalDate.parse(goal.getDeadline().format(formatter)));

          // Associando o usuário corretamente
        return response;
    }


}
