package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Transaction;
import com.dev.hakeem.nextcash.entity.Transferenca;
import com.dev.hakeem.nextcash.web.response.*;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public TransactionResponse mapToResponse(Transaction transaction) {
        Object detalhes = null;

        switch (transaction.getTipo()) {
            case RECEITA:
                detalhes = new IncomeResponse(
                        transaction.getIncome().getId(),
                        transaction.getIncome().getCategoryIncome(),
                        transaction.getIncome().getAmount(),
                        transaction.getIncome().getDescricao()
                );
                break;
            case DESPESA:
                detalhes = new ExpensaResponse(
                        transaction.getExpense().getId(),
                        transaction.getExpense().getCategoryExpense(),
                        transaction.getExpense().getAmount(),
                        transaction.getExpense().getDescricao(),
                        transaction.getExpense().getCreatedAt()

                );
                break;
            case TRANSFERENCA:
                detalhes = new TransactiontTransferenciaResponse(
                        transaction.getTransferenca().getId(),
                        transaction.getTransferenca().getValor(),
                        transaction.getTransferenca().getAccountOrigem().getAccountType().name(), // Tipo da conta de origem
                        transaction.getTransferenca().getAccountOrigem().getFinancialInstitution(), // Instituição financeira da conta de origem
                        transaction.getTransferenca().getAccountOrigem().getUserid().getUsername(), // Usuário associado à conta de origem
                        transaction.getTransferenca().getAccountDestino().getAccountType().name(), // Tipo da conta de destino
                        transaction.getTransferenca().getAccountDestino().getFinancialInstitution(), // Instituição financeira da conta de destino
                        transaction.getTransferenca().getAccountDestino().getUserid().getUsername() // Usuário associado à conta de destino
                );
                break;


        }

        return new TransactionResponse(
                 // Certifique-se que o valor seja do tipo Double
                transaction.getTipo(), // Certifique-se que o tipo seja do enum TransactionType
                detalhes

        );
    }


    public TransactiontTransferenciaResponse toTransferencaResponse(Transferenca transferencia) {
        return modelMapper.map(transferencia, TransactiontTransferenciaResponse.class);
    }

    @PostConstruct
    public void init() {
        modelMapper.typeMap(Transferenca.class, TransactiontTransferenciaResponse.class)
                .addMapping(src -> src.getAccountOrigem().getId(), TransactiontTransferenciaResponse::setAccountOrigem)
                .addMapping(src -> src.getAccountDestino().getId(), TransactiontTransferenciaResponse::setAccountDestino);
    }



}
