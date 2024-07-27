package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Cartao;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.CartaoRepository;
import com.dev.hakeem.nextcash.web.request.CartaoRequest;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartaoMapper {

    private  final AccountRepository accountRepository;
    private  final CartaoRepository repository;
    @Autowired
    public CartaoMapper(AccountRepository accountRepository, CartaoRepository repository) {
        this.accountRepository = accountRepository;
        this.repository = repository;
    }

    public Cartao toRequest(CartaoRequest request){
        Cartao cartao = new Cartao();
        cartao.setId(request.getId());
        cartao.setDescricao(request.getDescricao());
        cartao.setLimite(request.getLimite());
        cartao.setMarcaCartao(request.getMarcaCartao());
        cartao.setFechamento(request.getFechamento());
        cartao.setVencimento(request.getVencimento());
        Account account = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Conta nao encontrada"));
        cartao.setAccount(account);
        return cartao;
    }

    public CartaoResponse toResponse(Cartao cartao) {
        CartaoResponse response = new CartaoResponse();
        response.setId(cartao.getId());
        response.setDescricao(cartao.getDescricao());
        response.setMarcaCartao(cartao.getMarcaCartao());
        response.setLimite(cartao.getLimite());
        response.setFechamento(cartao.getFechamento());
        response.setVencimento(cartao.getVencimento());

        // Definir o ID da conta associada
        if (cartao.getAccount() != null) {
            response.setAccount(cartao.getAccount().getId());
        } else {
            throw new EntityNotFoundException("Conta nao encontrada");
        }

        return response;
    }
}
