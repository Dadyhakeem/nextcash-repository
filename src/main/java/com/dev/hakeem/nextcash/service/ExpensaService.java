package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Expense;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.ExpenseRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.request.ExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class ExpensaService {

    private  final ExpenseRepository repository;

    private  final AccountRepository accountRepository;
    private final TranssactionRepository transsactionRepository;

    @Autowired
    public ExpensaService(ExpenseRepository repository, AccountRepository accountRepository, TranssactionRepository transsactionRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.transsactionRepository = transsactionRepository;
    }


    public Expense createExpense(ExpenseRequest request){

        // Cria um novo objeto Expense
        Expense expense = new Expense();
        expense.setId(request.getId());
        expense.setDescricao(request.getDescricao());
        expense.setAmount(request.getAmount());
       expense.setCategoryExpense(request.getCategoryExpense());

        // Busca a conta e a transação associadas aos IDs fornecidos
        Account acc = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Account   não encontrado"));
        Transaction transaction = transsactionRepository.findById(request.getTransaction())
                .orElseThrow(()-> new EntityNotFoundException("Transaction  não encontrado"));

        // Configura a receita com a conta e a transação
        expense.setAccount(acc);
        expense.setTransaction(transaction);

        // Credita o valor na conta
        debitar(acc, request.getAmount());
        return repository.save(expense);
    }

    public List<Expense> listarTodos(){
        return repository.findAll();
    }

    public void deletarPorId(Long id ){
        Expense expense = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Receita do id %s  não encontrado",id)));
        repository.delete(expense);
    }

    public Expense buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Receita do id %s não encontrado",id)));
    }

    public  Expense editarIncome(ExpenseRequest request){
        Expense expense = repository.findById(request.getId())
                .orElseThrow(()-> new EntityNotFoundException("Receita não encontrado"));
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
        return repository.save(expense);
    }

    private void debitar(Account destino, Double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para debitar deve ser positivo.");
        }
        destino.setBalance(destino.getBalance() - valor);

    }

}
