package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.entity.Transferenca;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.TransferencaRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.request.TransferencaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferencaService {

    private final TransferencaRepository repository;
    private final AccountRepository accountRepository;
    private final TranssactionRepository transsactionRepository;

    @Autowired
    public TransferencaService(TransferencaRepository repository, AccountRepository accountRepository, TranssactionRepository transsactionRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.transsactionRepository = transsactionRepository;
    }

    @Transactional
    public void createTransfer(TransferencaRequest request) {
        // Valida se as contas origem e destino foram especificadas
        if (request.getAccountOrigem() == null || request.getAccountDestino() == null) {
            throw new IllegalArgumentException("Conta origem e destino devem ser especificadas.");
        }

        // Valida se o valor da transferência é positivo
        if (request.getValor() <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        // Obtém as contas e a transação do repositório
        Account accountOrigem = accountRepository.findById(request.getAccountOrigem())
                .orElseThrow(() -> new EntityNotFoundException("Conta origem não encontrada."));
        Account accountDestino = accountRepository.findById(request.getAccountDestino())
                .orElseThrow(() -> new EntityNotFoundException("Conta destino não encontrada."));
        Transaction transaction = transsactionRepository.findById(request.getTransaction())
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada."));

        // Lógica de transferência
        if (accountOrigem.getBalance() < request.getValor()) {
            throw new IllegalArgumentException("Saldo insuficiente na conta origem.");
        }
        accountOrigem.setBalance(accountOrigem.getBalance() - request.getValor());
        accountDestino.setBalance(accountDestino.getBalance() + request.getValor());

        // Salva as contas e a transação no repositório
        accountRepository.save(accountOrigem);
        accountRepository.save(accountDestino);
        transsactionRepository.save(transaction);
    }


    public Transferenca obterTranferPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transferência não encontrada"));
    }

    public void deletPorId(Long id) {
        if(!repository.existsById(id)){
                throw new EntityNotFoundException(String.format("Transaction id = %s não encontrado",id));
        }
        repository.deleteById(id);
    }

    // Métodos  para debitar e creditar
    private void debitar(Account origem, Double valor) {
        if (origem.getBalance() < valor) {
            throw new IllegalArgumentException("Saldo insuficiente na conta de origem.");
        }
        origem.setBalance(origem.getBalance() - valor);
    }

    private void creditar(Account destino, Double valor) {
        destino.setBalance(destino.getBalance() + valor);
    }
}
