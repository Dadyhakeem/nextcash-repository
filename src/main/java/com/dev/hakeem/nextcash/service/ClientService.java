package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Client;
import com.dev.hakeem.nextcash.exception.CpfUniqueViolationExeption;
import com.dev.hakeem.nextcash.repository.ClientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ClientService {

    private  final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Client salvar(Client client){
        try {
            return repository.save(client);
        }catch (DataIntegrityViolationException e){
          throw  new CpfUniqueViolationExeption(
                  String.format("CPF nao pode ser cadastrado, já eiste no sistema",client.getCpf()));
        }
    }




}
