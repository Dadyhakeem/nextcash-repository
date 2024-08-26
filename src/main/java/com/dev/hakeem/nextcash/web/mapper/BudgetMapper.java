package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Budget;
import com.dev.hakeem.nextcash.entity.Category;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.AccountType;
import com.dev.hakeem.nextcash.enums.CategoryExpense;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.BudgetRepository;
import com.dev.hakeem.nextcash.repository.CategoryRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.BudgetRequest;
import com.dev.hakeem.nextcash.web.response.BudgetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Component
public class BudgetMapper {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private  final BudgetRepository repository;
    @Autowired
    public BudgetMapper(CategoryRepository categoryRepository, UserRepository userRepository, BudgetRepository repository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.repository = repository;
    }



    public Budget toRequest(BudgetRequest request) {
        Budget budget = new Budget();
        budget.setAmount(request.getAmount());

        try {
            // Converte as strings para LocalDate
            LocalDate startDate = LocalDate.parse(request.getStartDate());
            LocalDate endDate = LocalDate.parse(request.getEndDate());

            budget.setStartDate(startDate);
            budget.setEndDate(endDate);
        } catch (DateTimeParseException e) {
            // Lida com o erro de parsing de data
            throw new IllegalArgumentException("Formato de data inválido", e);
        }

        try {
            CategoryExpense categoryExpense = CategoryExpense.valueOf(request.getCategoryExpense());
            budget.setCategoryExpense(categoryExpense);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Tipo de conta inválido: " + request.getCategoryExpense());
        }

        Optional<User> user = userRepository.findById(request.getUserId());
        if (!user.isPresent()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        budget.setUser(user.get());

        return budget;
    }


    public BudgetResponse toResponse(Budget budget) {
        BudgetResponse response = new BudgetResponse();
        response.setId(budget.getId());
        response.setAmount(budget.getAmount());
        // Convertendo CategoryExpense para String se categoryExpense espera uma String
        String categoryExpense = budget.getCategoryExpense()!= null ? budget.getCategoryExpense().name() : null;
        response.setCategoryExpense(categoryExpense);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        response.setStartDate(budget.getStartDate().format(formatter)); // Formata LocalDate para String
        response.setEndDate(budget.getEndDate().format(formatter));     // Formata LocalDate para String

        return response;
    }
}
