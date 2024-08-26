package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.enums.MarcaCartao;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoResponse {



        private Long id;


        private Long account;

        private String descricao;


        private String marcaCartao;


        private Double limite;


        private LocalDate fechamento;


        private LocalDate vencimento;


}
