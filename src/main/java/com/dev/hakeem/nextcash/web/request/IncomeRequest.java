package com.dev.hakeem.nextcash.web.request;

import com.dev.hakeem.nextcash.enums.CategoryIncome;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @AllArgsConstructor
@NoArgsConstructor
@Setter
public class IncomeRequest {


    private Long id;

    @NotNull(message = "Categoria não pode ser nula")
    private CategoryIncome categoryIncome;

    @NotNull(message = "Valor não pode ser nulo")
    @Positive(message = "O valor deve ser positivo")
    private Double amount;

    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;

    @NotNull(message = "Conta não pode ser nula")
    @Positive(message = "ID da conta deve ser positivo")
    private Long account;

    @NotNull(message = "Transação não pode ser nula")
    @Positive(message = "ID da transação deve ser positivo")
    private Long transaction;
}
