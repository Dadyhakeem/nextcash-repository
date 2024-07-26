package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.entity.Transferenca;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.TranssactionRepository;
import com.dev.hakeem.nextcash.web.request.TransferencaRequest;
import com.dev.hakeem.nextcash.web.response.TransferencaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransfeencaMapper {

    private final AccountRepository accountRepository;
    private final TranssactionRepository transsactionRepository;

    @Autowired
    public TransfeencaMapper(AccountRepository accountRepository, TranssactionRepository transsactionRepository) {
        this.accountRepository = accountRepository;
        this.transsactionRepository = transsactionRepository;
    }

    public Transferenca toRequest(TransferencaRequest request) {
        Transferenca transferenca = new Transferenca();
        transferenca.setDescricao(request.getDescricao());

        Account accountOrigem = accountRepository.findById(request.getAccountOrigem())
                .orElseThrow(() -> new IllegalArgumentException("Conta origem não encontrada"));
        Account accountDestino = accountRepository.findById(request.getAccountDestino())
                .orElseThrow(() -> new IllegalArgumentException("Conta destino não encontrada"));
        Transaction transaction = transsactionRepository.findById(request.getTransaction())
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));

        transferenca.setAccountOrigem(accountOrigem);
        transferenca.setAccountDestino(accountDestino);
        transferenca.setTransaction(transaction);
        transferenca.setValor(request.getValor());

        return transferenca;
    }

    public TransferencaResponse toResponse(Transferenca transferenca) {
        TransferencaResponse response = new TransferencaResponse();
        response.setId(transferenca.getId());
        response.setDescricao(transferenca.getDescricao());
        response.setValor(transferenca.getValor());

        // Incluindo informações detalhadas da conta destino, se necessário
        if (transferenca.getAccountDestino() != null) {
            response.setAccountDestino(transferenca.getAccountDestino().getId());
            response.setInsti(transferenca.getAccountDestino().getFinancialInstitution());
        }

        return response;
    }
}
