package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.enums.CategoryIncome;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class IncomeResponse {


    private Long id;

    @NotNull(message = "Categoria não pode ser nula")

    private CategoryIncome categoryIncome;

    @NotNull(message = "Valor não pode ser nulo")
    @Positive(message = "O valor deve ser positivo")
    private Double amount;

    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;
}
