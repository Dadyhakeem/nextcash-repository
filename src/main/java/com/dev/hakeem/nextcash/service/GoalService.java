package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Goal;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.repository.GoalRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.GoalRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    private final GoalRepository repository;
    private  final UserRepository userRepository;

    public GoalService(GoalRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

         public Goal createGoal(GoalRequest request){
             Optional<User> users = userRepository.findById(request.getUserid());

             if (!users.isPresent()){
                  new BusinessException("Usuario nao encontrada");
             }

             Goal gol = new Goal();
             gol.setId(request.getId());
             gol.setName(request.getName());
             gol.setTargetAmount(request.getTargetAmount());
             gol.setCurrentAmount(request.getCurrentAmount());
             gol.setDeadline(request.getDeadline());
             gol.setUserid(users.get());
             return repository.save(gol);
         }
          // listar
         public List<Goal> ListarTodos(){
            return repository.findAll();
         }

         public Optional<Goal>buscarPorId(Long id){
              return repository.findById(id);
         }

    public Goal atualizar(GoalRequest request) {

        Goal goal = repository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("Goal não encontrada"));
        if (request.getName() != null && !request.getName().isBlank()) {
            goal.setName(request.getName());
        }
        if (request.getTargetAmount() != null && request.getTargetAmount() >= 0) {
            goal.setTargetAmount(request.getTargetAmount());
        }
        if (request.getCurrentAmount() != null && request.getCurrentAmount() >= 0) {
            goal.setCurrentAmount(request.getCurrentAmount());
        }
        if (request.getDeadline() != null) {
            goal.setDeadline(request.getDeadline());
        }


        return repository.save(goal);
    }



    public void deletarPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Goal não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }




}
