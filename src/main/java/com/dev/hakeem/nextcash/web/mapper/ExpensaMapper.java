package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Expense;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.enums.CategoryExpense;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.*;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.formatter.CartaoRequestConverter;
import com.dev.hakeem.nextcash.web.request.ExpenseRequest;
import com.dev.hakeem.nextcash.web.response.ExpensaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        expense.setDescricao(request.getDescricao());
        expense.setAmount(request.getAmount());
        try {
            CategoryExpense categoryExpense = CategoryExpense.valueOf(request.getCategoryExpense());
            expense.setCategoryExpense(categoryExpense);
        }catch (IllegalArgumentException e ){
            throw new BusinessException("Tipo de despesa nao existe : " + request.getCategoryExpense());
        }
        Account acc = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Account   não encontrado"));

        expense.setAccount(acc);
        try{
            LocalDate createdAt = LocalDate.parse(request.getCreatedAt());
            expense.setCreatedAt(createdAt);
        } catch (DateTimeParseException e) {
            // Lida com o erro de parsing de data
            throw new IllegalArgumentException("Formato de data inválido", e);
        }


        return expense;
    }

    public ExpensaResponse toResponse(Expense expense){
        ExpensaResponse response = new ExpensaResponse();
        response.setId(expense.getId());
        response.setDescricao(expense.getDescricao());
        response.setAmount(expense.getAmount());
        response.setCategoryExpense(expense.getCategoryExpense() != null ? expense.getCategoryExpense().name(): null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        response.setCreatedAt(LocalDate.parse(expense.getCreatedAt().format(formatter)));



        return response;
    }
}