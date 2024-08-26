package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Cartao;
import com.dev.hakeem.nextcash.enums.MarcaCartao;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.CartaoRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.formatter.CartaoRequestConverter;
import com.dev.hakeem.nextcash.web.request.CartaoRequest;
import com.dev.hakeem.nextcash.web.response.CartaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        cartao.setDescricao(request.getDescricao());
        cartao.setLimite(request.getLimite());
        try {
            MarcaCartao marcaCartao = MarcaCartao.valueOf(request.getMarcaCartao());
            cartao.setMarcaCartao(marcaCartao);
        }catch (IllegalArgumentException e ){
            throw new BusinessException("Tipo de marca de cartao nao existe : " + request.getMarcaCartao());
        }
        try {
            // Converte as strings para LocalDate
            LocalDate vencimento = CartaoRequestConverter.parseDate(String.valueOf(request.getVencimento()));
            LocalDate fechamento = CartaoRequestConverter.parseDate(String.valueOf(request.getFechamento()));

            cartao.setFechamento(fechamento);
            cartao.setVencimento(vencimento);
        } catch (DateTimeParseException e) {
            // Lida com o erro de parsing de data
            throw new IllegalArgumentException("Formato de data inválido", e);
        }
        Account account = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Conta nao encontrada"));
        cartao.setAccount(account);
        return cartao;
    }

    public CartaoResponse toResponse(Cartao cartao) {
        CartaoResponse response = new CartaoResponse();
        response.setId(cartao.getId());
        response.setDescricao(cartao.getDescricao());

        // Corrigir o mapeamento da marca do cartão
        response.setMarcaCartao(cartao.getMarcaCartao() != null ? cartao.getMarcaCartao().name() : null);

        response.setLimite(cartao.getLimite());
        response.setFechamento(cartao.getFechamento());
        response.setVencimento(cartao.getVencimento());

        // Definir o ID da conta associada
        if (cartao.getAccount() != null) {
            response.setAccount(cartao.getAccount().getId());
        } else {
            throw new EntityNotFoundException("Conta não encontrada");
        }

        return response;
    }

}
