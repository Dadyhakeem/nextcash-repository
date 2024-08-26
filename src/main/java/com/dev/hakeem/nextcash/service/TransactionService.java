package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.*;
import com.dev.hakeem.nextcash.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private  final TranssactionRepository transsactionRepository;
    private final TransferencaRepository transferencaRepository;
    @Autowired
    public TransactionService(ExpenseRepository expenseRepository, IncomeRepository incomeRepository, TranssactionRepository transsactionRepository, TransferencaRepository transferencaRepository) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
        this.transsactionRepository = transsactionRepository;
        this.transferencaRepository = transferencaRepository;
    }


   public  List<Transaction>obterTodosasTransferencias(){
        List<Transaction> transactions = new ArrayList<>();

        List<Expense> despensas = expenseRepository.findAll();
        for (Expense expense: despensas){
            transactions.add(new Transaction(expense));
        }

       List<Income> incomes = incomeRepository.findAll();
       for (Income income: incomes){
           transactions.add(new Transaction(income));
       }

       List<Transferenca> transferencas =transferencaRepository.findAll();
       for (Transferenca transferenca: transferencas){
           transactions.add(new Transaction(transferenca));
       }
       return transactions;
   }

}
