package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Income;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.IncomeRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.request.IncomeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    private  final IncomeRepository repository;

    private  final AccountRepository accountRepository;
    private final TranssactionRepository transsactionRepository;


    public IncomeService(IncomeRepository repository, AccountRepository accountRepository, TranssactionRepository transsactionRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.transsactionRepository = transsactionRepository;
    }


    public Income createIncome(IncomeRequest request){

        // Cria um novo objeto Income
        Income income = new Income();
        income.setId(request.getId());
        income.setDescricao(request.getDescricao());
        income.setAmount(request.getAmount());
        income.setCategoryIncome(request.getCategoryIncome());

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
       return repository.findAll();
    }

    public void deletarPorId(Long id ){
        Income income = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Receita do id %s  não encontrado",id)));
        repository.delete(income);
    }

    public Income buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Receita do id %s não encontrado",id)));
    }

    public  Income editarIncome(IncomeRequest request){
        Income income = repository.findById(request.getId())
                .orElseThrow(()-> new EntityNotFoundException("Receita não encontrado"));

        income.setDescricao(request.getDescricao());
        income.setAmount(request.getAmount());
        income.setCategoryIncome(request.getCategoryIncome());
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


}
