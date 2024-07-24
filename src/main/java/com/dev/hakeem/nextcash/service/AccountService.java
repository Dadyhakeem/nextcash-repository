package com.dev.hakeem.nextcash.service;
import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.request.AccountRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AccountService {

    private final AccountRepository repository;
    private  final UserRepository userRepository;

    public AccountService(AccountRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Account createAccount(AccountRequest request) {
        // Verifica se o usuário existe no banco de dados
        Optional<User> userOptional = userRepository.findById(request.getUser());

        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        // Cria uma nova instância de Account
        Account account = new Account();
        account.setName(request.getName());
        account.setFinancialInstitution(request.getFinancialInstitution());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());
        account.setUser(userOptional.get());  // Define o usuário encontrado

        // Salva a conta no repositório e retorna
        return repository.save(account);
    }


    public Account buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Conta id = %s não encontrado",id)));

    }

    public List<Account> listarTodos(){
        return repository.findAll();
    }

    public void deleteAccount(Long id){
        Account account = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Conta id = %s não encontrado",id)));
        repository.delete(account);
    }
}
