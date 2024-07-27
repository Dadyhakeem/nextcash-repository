package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Cartao;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.CartaoRepository;
import com.dev.hakeem.nextcash.web.request.CartaoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoService {

    private final AccountRepository accountRepository;
    private  final CartaoRepository repository;

    @Autowired
    public CartaoService(AccountRepository accountRepository, CartaoRepository repository) {
        this.accountRepository = accountRepository;
        this.repository = repository;
    }

    public Cartao createCartao(CartaoRequest request){
        Account account = accountRepository.findById(request.getAccount())
                .orElseThrow(()-> new EntityNotFoundException("Conta nao encontrada"));

        Cartao cartao = new Cartao();
        cartao.setId(request.getId());
        cartao.setDescricao(request.getDescricao());
        cartao.setLimite(request.getLimite());
        cartao.setMarcaCartao(request.getMarcaCartao());
        cartao.setFechamento(request.getFechamento());
        cartao.setVencimento(request.getVencimento());
        cartao.setAccount(account);
        return repository.save(cartao);
    }

    public List<Cartao> listarTodos(){
        return repository.findAll();
    }

    public Cartao buscarPorId(Long id){
       return repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Cartao do id = %s nao encontrada",id)));
    }

    public void deletarPorId(Long id){
        Cartao cartao = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Cartao do id = %s nao encontrada",id)));
        repository.delete(cartao);
    }

    public Cartao atualizarCartao(CartaoRequest request) {
        // Verifica se a conta associada existe
        Account account = accountRepository.findById(request.getAccount())
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        // Obtém o cartão existente que será atualizado
        Cartao cartaoExistente = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cartão com ID %d não encontrado", request.getId())));

        // Atualiza os campos do cartão existente com os novos valores
        cartaoExistente.setDescricao(request.getDescricao());
        cartaoExistente.setLimite(request.getLimite());
        cartaoExistente.setMarcaCartao(request.getMarcaCartao());
        cartaoExistente.setFechamento(request.getFechamento());
        cartaoExistente.setVencimento(request.getVencimento());
        cartaoExistente.setAccount(account);

        // Salva as mudanças no banco de dados
        return repository.save(cartaoExistente);
    }

}
