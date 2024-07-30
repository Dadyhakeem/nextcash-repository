package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Client;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.ClientRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.request.ClientResquest;
import com.dev.hakeem.nextcash.web.response.ClientResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;

@Component
public class ClientMapper {

    private  final ClientRepository repository;
    private final UserRepository userRepository;
     @Autowired
    public ClientMapper(ClientRepository repository, UserRepository userRepository) {
        this.repository = repository;
         this.userRepository = userRepository;
     }

    public Client toResquest(ClientResquest resquest){
         Client client = new Client();
         client.setName(resquest.getName());
         client.setCpf(resquest.getCpf());
        User user = userRepository.findById(resquest.getUser())
                .orElseThrow(()-> new EntityNotFoundException("User nao encontrada"));
        client.setUser(user);
        return client;
    }

    public ClientResp toResponse(Client client){
         ClientResp response = new ClientResp();
         response.setId(client.getId());
         response.setName(client.getName());
         response.setCpf(client.getCpf());
         return response;
    }
}
