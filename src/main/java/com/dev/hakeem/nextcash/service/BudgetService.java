package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Budget;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.CategoryExpense;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.exception.NaoAuthorizado;
import com.dev.hakeem.nextcash.repository.BudgetRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.request.BudgetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        try {
            LocalDate startDate = LocalDate.parse(request.getStartDate());
            LocalDate endDate = LocalDate.parse(request.getEndDate());
            budget.setStartDate(startDate);
            budget.setEndDate(endDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido", e);
        }
        try {
            CategoryExpense categoryExpense = CategoryExpense.valueOf(request.getCategoryExpense().toUpperCase());
            budget.setCategoryExpense(categoryExpense);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoria de despesa inválida", e);
        }
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

    public Budget atualizar(Long id, BudgetRequest request) {
        // Encontrar o orçamento pelo ID
        Budget budget = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orçamento não encontrado"));

        // Verificar se o usuário é o proprietário do orçamento
        if (!budget.getUser().getId().equals(request.getUserId())) {
            throw new NaoAuthorizado("Usuário não autorizado a atualizar este orçamento");
        }

        // Atualizar campos
        budget.setAmount(request.getAmount());

        // Converter Strings para LocalDate com verificação de nulos
        if (request.getStartDate() != null && request.getEndDate() != null) {
            try {
                LocalDate startDate = LocalDate.parse(request.getStartDate());
                LocalDate endDate = LocalDate.parse(request.getEndDate());
                budget.setStartDate(startDate);
                budget.setEndDate(endDate);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Formato de data inválido", e);
            }
        }

        // Converter e validar categoryExpense com verificação de nulo
        if (request.getCategoryExpense() != null) {
            try {
                CategoryExpense categoryExpense = CategoryExpense.valueOf(request.getCategoryExpense().toUpperCase());
                budget.setCategoryExpense(categoryExpense);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Categoria de despesa inválida", e);
            }
        }

        // Salvar o orçamento atualizado
        return repository.save(budget);
    }





}
