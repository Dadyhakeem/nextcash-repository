package com.dev.hakeem.nextcash.web.request;

import com.dev.hakeem.nextcash.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaInfo {
    private Long id;
    private String nome;
    private String instituicao;
    private String tipo;
}

