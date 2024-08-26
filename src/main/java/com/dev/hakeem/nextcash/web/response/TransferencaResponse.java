package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferencaResponse  {


    private Double valor;
    private String descricao;
    private Account accountDestino;
    private Account accountOrigem;
    private Long id;
    private LocalDate data;


}
