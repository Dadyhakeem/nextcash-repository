package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.AccountType;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.AccountRequest;
import com.dev.hakeem.nextcash.web.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ToAccountMapper {

    private final UserRepository userRepository;

    @Autowired
    public ToAccountMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Account toRequast(AccountRequest request) {

        Account account = new Account();


        account.setFinancialInstitution(request.getFinancialInstitution());
        try {
            AccountType accountType = AccountType.valueOf(request.getAccountType());
            account.setAccountType(accountType);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Tipo de conta inválido: " + request.getAccountType());
        }
        account.setBalance(request.getBalance());


        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isPresent()) {
            account.setUserid(userOptional.get());
        } else {
            throw new BusinessException("Usuário não encontrado");
        }

        return account;
    }


    public AccountResponse toReponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setFinancialInstitution(account.getFinancialInstitution());

        // Converte AccountType para String se AccountResponse espera uma String
        String accountTypeString = account.getAccountType() != null ? account.getAccountType().name() : null;
        response.setAccountType(accountTypeString);

        response.setBalance(account.getBalance());
        return response;
    }
}
