package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.TransactionRequest;
import com.dev.hakeem.nextcash.web.response.TransactionResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class TransactionMapper {

    private  final AccountRepository accountRepository;
    private  final TranssactionRepository repository;

    public TransactionMapper(AccountRepository accountRepository, TranssactionRepository repository) {
        this.accountRepository = accountRepository;
        this.repository = repository;
    }

    public Transaction ToResquest(TransactionRequest request){


        Transaction transaction = new Transaction();
        transaction.setId(request.getId());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setAmount(request.getAmount());
        transaction.setDate(request.getDate());
        Optional<Account> account = accountRepository.findById(request.getAccountId());
        if (!account.isPresent()) {
               throw  new BusinessException("Conta nao encontrada");
        }
        transaction.setAccount(account.get());
        return transaction;

    }

    public TransactionResponse ToResponse(Transaction transaction){

        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setDate(transaction.getDate());
        response.setTransactionType(transaction.getTransactionType());
        response.setDescription(transaction.getDescription());
        response.setAmount(transaction.getAmount());
        return response;
    }
}
