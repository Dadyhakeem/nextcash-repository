package com.dev.hakeem.nextcash.web.request;

import com.dev.hakeem.nextcash.enums.MarcaCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoRequest {

    private Long id;

    @NotNull(message = "Conta não pode ser nula")
    @Positive(message = "ID da conta deve ser positivo")
    private Long account;

    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;

    @NotNull(message = "Marca do cartão não pode ser nula")
    private MarcaCartao marcaCartao;

    @NotNull(message = "Limite não pode ser nulo")
    @Positive(message = "Limite deve ser positivo")
    private Double limite;

    @NotNull(message = "Data de fechamento não pode ser nula")
    @PastOrPresent(message = "Data de fechamento deve ser no passado ou presente")
    private LocalDate fechamento;

    @NotNull(message = "Data de vencimento não pode ser nula")
    @Future(message = "Data de vencimento deve ser no futuro")
    private LocalDate vencimento;
}
