package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Client;
import com.dev.hakeem.nextcash.jwt.JwtUserDetails;
import com.dev.hakeem.nextcash.service.ClientService;
import com.dev.hakeem.nextcash.service.UserService;
import com.dev.hakeem.nextcash.web.mapper.ClientMapper;
import com.dev.hakeem.nextcash.web.request.ClientResquest;
import com.dev.hakeem.nextcash.web.response.ClientResp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClientController {

    private  final ClientService service;
    private  final UserService userService;
    @Autowired
    public ClientController(ClientService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResp> create (@RequestBody @Valid ClientResquest resquest,
                                              @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(resquest);
        client.setUser(userService.buscarPorId(userDetails.getId()));
        service.salvar(client);
        return  ResponseEntity.status(201).body(ClientMapper.toReponse(client));
    }
}
