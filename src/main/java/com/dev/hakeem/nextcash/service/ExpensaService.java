package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Expense;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.CategoryExpense;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.ExpenseRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.ExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
@Service
public class ExpensaService {

    private  final ExpenseRepository repository;
    private final UserService userService;

    private  final AccountRepository accountRepository;


    @Autowired
    public ExpensaService(ExpenseRepository repository, UserService userService, AccountRepository accountRepository ) {
        this.repository = repository;
        this.userService = userService;
        this.accountRepository = accountRepository;

    }


    public Expense createExpense(ExpenseRequest request){

        String authenticationEmail  = getAuthenticatedEmail();
        User authentificationUser = userService.BuscarPorEmail(authenticationEmail);


        // Cria um novo objeto Expense
        Expense expense = new Expense();
        expense.setDescricao(request.getDescricao());
        expense.setAmount(request.getAmount());
        try {
            CategoryExpense categoryExpense = CategoryExpense.valueOf(request.getCategoryExpense());
            expense.setCategoryExpense(categoryExpense);
        }catch (IllegalArgumentException e ){
            throw new BusinessException("Tipo de despesa nao existe : " + request.getCategoryExpense());
        }
        try {
            LocalDate createdAt = LocalDate.parse(request.getCreatedAt());
            expense.setCreatedAt(createdAt);
        }  catch (
    DateTimeParseException e) {
        throw new IllegalArgumentException("Formato de data inválido", e);
    }


        // Busca a conta  associadas ao ID fornecidos
        Account acc = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Account   não encontrado"));

        if (!acc.getUserid().getId().equals(authentificationUser.getId())) {
            throw new BusinessException("Você não tem permissão para adicionar despesas nesta conta.");
        }


        // Configura a receita com a conta e a transação
        expense.setAccount(acc);


        // Credita o valor na conta
        debitar(acc, request.getAmount());
        return repository.save(expense);
    }

    public List<Expense> listarTodos(){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        return repository.findAll();
    }

    public void deletarPorId(Long id ){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        Expense expense = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Despesa do id %s  não encontrado",id)));
        repository.delete(expense);
    }

    public Expense buscarPorId(Long id){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        return repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("despesa do id %s não encontrado",id)));
    }

    public  Expense editarIncome(ExpenseRequest request){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        Expense expense = repository.findById(request.getId())
                .orElseThrow(()-> new EntityNotFoundException("Despesa não encontrado"));

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
        return repository.save(expense);
    }

    private void debitar(Account destino, Double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para debitar deve ser positivo.");
        }
        destino.setBalance(destino.getBalance() - valor);

    }


    // Método para obter o e-mail do usuário autenticado
    private String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername(); // Retorna o e-mail
        }
        throw new SecurityException("Usuário não autenticado.");
    }

}
