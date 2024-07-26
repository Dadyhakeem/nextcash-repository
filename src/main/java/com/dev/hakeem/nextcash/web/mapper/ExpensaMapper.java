package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Expense;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.*;
import com.dev.hakeem.nextcash.web.request.ExpenseRequest;
import com.dev.hakeem.nextcash.web.response.ExpensaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpensaMapper {

    private final ExpenseRepository repository;

    private final AccountRepository accountRepository;
    private final TranssactionRepository transsactionRepository;

    @Autowired
    public ExpensaMapper(ExpenseRepository repository, CategoryRepository categoryRepository, AccountRepository accountRepository, TranssactionRepository transsactionRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.transsactionRepository = transsactionRepository;
    }

    public Expense toRequest(ExpenseRequest request){

        Expense expense = new Expense();
        expense.setId(request.getId());
        expense.setDescricao(request.getDescricao());
        expense.setAmount(request.getAmount());
        expense.setCategoryExpense(request.getCategoryExpense());
        Account acc = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Account   não encontrado"));
        Transaction transaction = transsactionRepository.findById(request.getTransaction())
                .orElseThrow(()-> new EntityNotFoundException("Transaction  não encontrado"));
        expense.setAccount(acc);
        expense.setTransaction(transaction);
        return expense;
    }

    public ExpensaResponse toResponse(Expense expense){
        ExpensaResponse response = new ExpensaResponse();
        response.setId(expense.getId());
        response.setDescricao(expense.getDescricao());
        response.setAmount(expense.getAmount());
        response.setCategoryExpense(expense.getCategoryExpense());


        return response;
    }
}