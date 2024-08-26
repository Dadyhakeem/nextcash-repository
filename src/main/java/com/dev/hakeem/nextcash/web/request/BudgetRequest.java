package com.dev.hakeem.nextcash.web.request;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BudgetRequest {



    @NotNull(message = "Balance cannot be null")
    @Positive(message = "Balance deve ser  positive")
    private Double amount;

    @NotBlank(message = "A data de início não pode ser nula")
    private String startDate;

    @NotBlank(message = "A data de término não pode ser nula")
    private String endDate;

    @NotNull(message = "O usuário não pode ser nulo")
    private Long userId;

    @NotBlank(message = "CategoryExpense")
    private String categoryExpense;
}
