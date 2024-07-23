package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Budget;
import com.dev.hakeem.nextcash.entity.Category;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.repository.BudgetRepository;
import com.dev.hakeem.nextcash.repository.CategoryRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.BudgetRequest;
import com.dev.hakeem.nextcash.web.response.BudgetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Budget toRequest(BudgetRequest request){

        Budget budget = new Budget();
        budget.setId(request.getId());
        budget.setAmount(request.getAmount());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());

        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if (user.isEmpty()) {
            throw new BusinessException("Usuário não encontrado");
        }
        if (category.isEmpty()) {
            throw new BusinessException("Categoria não encontrada");
        }

        budget.setUser(user.get());
        budget.setCategory(category.get());
        return budget;
    }

    public BudgetResponse toResponse(Budget budget){
        BudgetResponse response = new BudgetResponse();
        response.setId(budget.getId());
        response.setAmount(budget.getAmount());
        response.setStartDate(budget.getStartDate());
        response.setEndDate(budget.getEndDate());

        return response;

    }
}
