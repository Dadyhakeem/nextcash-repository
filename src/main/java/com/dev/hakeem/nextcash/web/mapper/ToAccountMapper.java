package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.AccountRequest;
import com.dev.hakeem.nextcash.web.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ToAccountMapper {

    private  final UserRepository userRepository;
     @Autowired
    public ToAccountMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Account toRequast(AccountRequest request) {

        Account account = new Account();



        account.setFinancialInstitution(request.getFinancialInstitution());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());

        // Associa o usuário à conta
        // Aqui, você precisa usar o ID do usuário para buscar o usuário correspondente e definir na conta
        Optional<User> userOptional = userRepository.findById(request.getUser());
        if (userOptional.isPresent()) {
            account.setUser(userOptional.get());
        } else {
            throw new BusinessException("Usuário não encontrado");
        }

        return account;
    }



    public AccountResponse toReponse(Account account){
        AccountResponse response =  new AccountResponse();
        response.setId(account.getId());
        response.setFinancialInstitution(account.getFinancialInstitution());
       response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        return response;
    }
}
