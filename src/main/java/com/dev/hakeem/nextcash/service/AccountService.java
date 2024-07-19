package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.AccountRequest;

import java.util.List;
import java.util.Optional;

public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account createAccount(AccountRequest request){
        Account account = new Account();

        account.setName(request.getName());
        account.setFinancialInstitution(request.getFinancialInstitution());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());
        return repository.save(account);
    }

    public Account buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

    }

    public List<Account> listarTodos(){
        return repository.findAll();
    }

    public void deleteAccount(Long id){
        Account account = repository.findById(id)
                .orElseThrow(()-> new BusinessException("Conta na encontrada"));
        repository.delete(account);
    }
}
