package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Goal;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.GoalRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.request.GoalRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    private final GoalRepository repository;
    private  final UserRepository userRepository;
    private  final UserService userService;

    public GoalService(GoalRepository repository, UserRepository userRepository, UserService userService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

         public Goal createGoal(GoalRequest request){
             String authenticationEmail  = getAuthenticatedEmail();
             User authentificationUser = userService.BuscarPorEmail(authenticationEmail);

             Optional<User> users = userRepository.findById(request.getUserid());

             if (!users.isPresent()){
                  new EntityNotFoundException("Usuario nao encontrada");
             }

             Goal gol = new Goal();
             gol.setName(request.getName());
             gol.setTargetAmount(request.getTargetAmount());
             gol.setCurrentAmount(request.getCurrentAmount());
             try {
                 LocalDate deadline = LocalDate.parse(request.getDeadline());
                 gol.setDeadline(deadline);
             } catch (DateTimeParseException e) {
                 // Lida com o erro de parsing de data
                 throw new IllegalArgumentException("Formato de data inválido", e);
             }

             gol.setUserid(users.get());
             return repository.save(gol);
         }
          // listar
         public List<Goal> ListarTodos(){
             String authenticatedEmail = getAuthenticatedEmail();
             User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
            return repository.findAll();
         }

         public Goal buscarPorId(Long id){
             String authenticatedEmail = getAuthenticatedEmail();
             User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
              return repository.findById(id)
                      .orElseThrow(()-> new EntityNotFoundException(String.format("meta do id %s não encontrado",id)));
         }

    public Goal atualizar(Long id,GoalRequest request) {
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);

        Goal goal = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Goal não encontrada"));
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
            try {
                LocalDate deadline = LocalDate.parse(request.getDeadline());
                goal.setDeadline(deadline);
            } catch (DateTimeParseException e) {
                // Lida com o erro de parsing de data
                throw new IllegalArgumentException("Formato de data inválido", e);
            }
        }


        return repository.save(goal);
    }



    public void deletarPorId(Long id) {
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Conta id = %s não encontrado",id));
        }
        repository.deleteById(id);
    }

    // Método para obter o e-mail do usuário autenticado
    private String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername(); // Retorna o e-mail
        }
        throw new SecurityException("Usuário não autenticado.");
    }




}
