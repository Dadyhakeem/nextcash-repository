package com.dev.hakeem.nextcash.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactiontTransferenciaResponse {

    private Long id;
    private Double valor;
    private String accountOrigem; // Tipo da conta de origem (ex: Conta Corrente)
    private String instituicaoOrigem; // Instituição financeira da conta de origem
    private String usuarioOrigem; // Nome do usuário associado à conta de origem
    private String accountDestino; // Tipo da conta de destino (ex: Poupança)
    private String instituicaoDestino; // Instituição financeira da conta de destino
    private String usuarioDestino; // Nome do usuário associado à conta de destino
}
