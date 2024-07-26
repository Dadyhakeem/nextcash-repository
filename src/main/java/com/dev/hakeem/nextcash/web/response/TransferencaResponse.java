package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.entity.Account;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferencaResponse {


    private Double valor;


    private String descricao;


    private Long accountDestino;

    private Long id;

    private String insti;

    private Double balanceContaDestino;

    private Timestamp data;
}
