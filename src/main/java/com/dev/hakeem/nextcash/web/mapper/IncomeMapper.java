package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Category;
import com.dev.hakeem.nextcash.entity.Income;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.CategoryRepository;
import com.dev.hakeem.nextcash.repository.IncomeRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.request.IncomeRequest;
import com.dev.hakeem.nextcash.web.response.IncomeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        income.setId(request.getId());
        income.setDescricao(request.getDescricao());
        income.setAmount(request.getAmount());
        income.setCategoryIncome(request.getCategoryIncome());
        Account acc = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Account   não encontrado"));
        Transaction transaction = transsactionRepository.findById(request.getTransaction())
                .orElseThrow(()-> new EntityNotFoundException("Transaction  não encontrado"));
        income.setAccount(acc);
        income.setTransaction(transaction);
        return income;
    }

    public IncomeResponse toResponse(Income income){
        IncomeResponse response = new IncomeResponse();
        response.setId(income.getId());
        response.setDescricao(income.getDescricao());
        response.setAmount(income.getAmount());
        response.setCategoryIncome(income.getCategoryIncome());


        return response;
    }
}
