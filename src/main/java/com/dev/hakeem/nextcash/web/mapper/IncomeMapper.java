package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Category;
import com.dev.hakeem.nextcash.entity.Income;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.enums.CategoryIncome;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.CategoryRepository;
import com.dev.hakeem.nextcash.repository.IncomeRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.IncomeRequest;
import com.dev.hakeem.nextcash.web.response.IncomeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
@Component
public class IncomeMapper {

    private  final IncomeRepository repository;
    private  final CategoryRepository categoryRepository;
    private  final AccountRepository accountRepository;
    private final TranssactionRepository transsactionRepository;

    @Autowired
    public IncomeMapper(IncomeRepository repository, CategoryRepository categoryRepository, AccountRepository accountRepository, TranssactionRepository transsactionRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
        this.transsactionRepository = transsactionRepository;
    }

    public Income toRequest(IncomeRequest request){

        Income income = new Income();
        income.setDescricao(request.getDescricao());
        income.setAmount(request.getAmount());
        try{
            CategoryIncome categoryIncome = CategoryIncome.valueOf(request.getCategoryIncome());
            income.setCategoryIncome(categoryIncome);
        }catch (IllegalArgumentException e ){
            throw new BusinessException("Tipo de Receita nao existe : " + request.getCategoryIncome());
        }
        try {
            LocalDate created_at = LocalDate.parse(request.getCreated_at());
            income.setCreatedAt(created_at);
        }catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido", e);
        }
        Account acc = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Account   não encontrado"));

        income.setAccount(acc);

        return income;
    }

    public IncomeResponse toResponse(Income income){
        IncomeResponse response = new IncomeResponse();
        response.setId(income.getId());
        response.setDescricao(income.getDescricao());
        response.setAmount(income.getAmount());
        response.setCategoryIncome(income.getCategoryIncome() != null ? income.getCategoryIncome().name() : null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        response.setCreated_at(LocalDate.parse(income.getCreatedAt().format(formatter)));

        return response;
    }
}
