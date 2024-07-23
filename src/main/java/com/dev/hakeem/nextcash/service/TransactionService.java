package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TranssactionRepository repository;
    private final AccountRepository accountRepository;
    @Autowired
    public TransactionService(TranssactionRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    public Transaction createTransaction(TransactionRequest request){
        Optional<Account> account = accountRepository.findById(request.getAccountId());
        if (!account.isPresent()) {
            throw  new BusinessException("Conta nao encontrada");
        }
            Transaction transaction = new Transaction();
            transaction.setId(request.getId());
            transaction.setDescription(request.getDescription());
            transaction.setTransactionType(request.getTransactionType());
            transaction.setAmount(request.getAmount());
            transaction.setDate(request.getDate());

            transaction.setAccount(account.get());
            return repository.save(transaction);


    }

    public void deletar(Long id){
         if (!repository.existsById(id)){
             throw new BusinessException("Transaction nao encontrada");
         }
         repository.deleteById(id);
    }

    public Transaction buscarPorid(Long id){
       return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Transaction não encontrado"));
    }

    public List<Transaction> listarTodos(){
       return repository.findAll();
    }
}
