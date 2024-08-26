package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Cartao;
import com.dev.hakeem.nextcash.enums.MarcaCartao;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.exception.NaoAuthorizado;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.CartaoRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.formatter.CartaoRequestConverter;
import com.dev.hakeem.nextcash.web.request.CartaoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        cartao.setDescricao(request.getDescricao());
        cartao.setLimite(request.getLimite());
        // convertendo o enum em String
        try {
            MarcaCartao marcaCartao = MarcaCartao.valueOf(request.getMarcaCartao());
            cartao.setMarcaCartao(marcaCartao);
        }catch (IllegalArgumentException e){
            throw new BusinessException("Tipo de marca de cartao nao e valido: " + request.getMarcaCartao());
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

    public Cartao atualizarCartao(Long id, CartaoRequest request) {
        // Verifica se a conta associada existe
        Account account = accountRepository.findById(request.getAccount())
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        // Obtém o cartão existente que será atualizado
        Cartao cartaoExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cartão com ID %d não encontrado", id)));
        // Verificar se o usuário é o proprietário do orçamento
        if (!cartaoExistente.getAccount().getId().equals(request.getAccount())) {
            throw new NaoAuthorizado("Usuário não autorizado a atualizar este cartao");
        }

        // Atualiza os campos do cartão existente com os novos valores
        cartaoExistente.setDescricao(request.getDescricao());
        cartaoExistente.setLimite(request.getLimite());
        try {
            MarcaCartao marcaCartao = MarcaCartao.valueOf(request.getMarcaCartao());
            cartaoExistente.setMarcaCartao(marcaCartao);
        }catch (IllegalArgumentException e){
            throw new BusinessException("Tipo de marca de cartao nao e valido: " + request.getMarcaCartao());
        }
        try {
            // Converte as strings para LocalDate
            LocalDate vencimento = CartaoRequestConverter.parseDate(String.valueOf(request.getVencimento()));
            LocalDate fechamento = CartaoRequestConverter.parseDate(String.valueOf(request.getFechamento()));

            cartaoExistente.setFechamento(fechamento);
            cartaoExistente.setVencimento(vencimento);
        } catch (DateTimeParseException e) {
            // Lida com o erro de parsing de data
            throw new IllegalArgumentException("Formato de data inválido", e);
        }
        cartaoExistente.setAccount(account);

        // Salva as mudanças no banco de dados
        return repository.save(cartaoExistente);
    }

}
