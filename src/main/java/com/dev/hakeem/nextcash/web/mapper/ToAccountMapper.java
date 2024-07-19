package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.web.request.AccountRequest;
import com.dev.hakeem.nextcash.web.response.AccountResponse;

public class ToAccountMapper {


    public Account ToRequast(AccountRequest request){
        Account account = new Account();

        account.setName(request.getName());
        account.setFinancialInstitution(request.getFinancialInstitution());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());
        return account;
    }


    public AccountResponse ToReponse(Account account){
        AccountResponse response =  new AccountResponse();
        response.setId(account.getId());
        response.setName(account.getName());
        response.setFinancialInstitution(account.getFinancialInstitution());
       response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        return response;
    }
}
