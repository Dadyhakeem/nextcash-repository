package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Income;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.CategoryIncome;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.IncomeRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.IncomeRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class IncomeService {

    private  final IncomeRepository repository;

    private  final AccountRepository accountRepository;
    private final TranssactionRepository transsactionRepository;
    private final UserService userService;


    public IncomeService(IncomeRepository repository, AccountRepository accountRepository, TranssactionRepository transsactionRepository, UserService userService) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.transsactionRepository = transsactionRepository;
        this.userService = userService;
    }


    public Income createIncome(IncomeRequest request){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);

        // Cria um novo objeto Income
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

        // Busca a conta e a transação associadas aos IDs fornecidos
        Account acc = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Account   não encontrado"));


        // a receita com a conta
        income.setAccount(acc);

        // Credita o valor na conta
        creditar(acc, request.getAmount());
        return repository.save(income);
    }

    public List<Income> listarTodos(){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        return repository.findAll();
    }

    public void deletarPorId(Long id ){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        Income income = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Receita do id %s  não encontrado",id)));
        repository.delete(income);
    }

    public Income buscarPorId(Long id){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        return repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Receita do id %s não encontrado",id)));
    }

    public  Income editarIncome(Long id ,IncomeRequest request){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        Income income = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Receita não encontrado"));

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
        return repository.save(income);
    }

    private void creditar(Account destino, Double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para crédito deve ser positivo.");
        }
        destino.setBalance(destino.getBalance() + valor);

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
