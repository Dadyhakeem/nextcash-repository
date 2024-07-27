package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Budget;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.BudgetRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.request.BudgetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BudgetService {

    private final UserRepository userRepository;
    private  final BudgetRepository repository;

     @Autowired
    public BudgetService(UserService userService, UserRepository userRepository, BudgetRepository repository) {
         this.userRepository = userRepository;
        this.repository = repository;
    }

    public Budget createBudget(BudgetRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Budget budget = new Budget();
        budget.setAmount(request.getAmount());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        budget.setCategoryExpense(request.getCategoryExpense());
        budget.setUser(user); //  relacionamento  bidirecional
        return repository.save(budget);
    }

    public List<Budget> listarTodas(){
         return repository.findAll();
    }

    public  void deletaPeloId(Long id){
        if (!repository.existsById(id)){
            throw new EntityNotFoundException(String.format("Orçamento id = %s não encontrado",id));
        }
        repository.deleteById(id);

    }

    public Budget buscarPeloId(Long id){
        return repository.findById(id)
                 .orElseThrow(()-> new EntityNotFoundException(String.format("Orçamento id = %s não encontrado",id)));

    }

    public Budget atualizar(BudgetRequest request) {

        Budget budget = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Orçamento não encontrado"));

        if (!budget.getUser().getId().equals(request.getUserId())) {
            throw new EntityNotFoundException("Usuário não autorizado a atualizar este orçamento");
        }
        budget.setAmount(request.getAmount());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        budget.setCategoryExpense(request.getCategoryExpense());
        return repository.save(budget);
    }



}
