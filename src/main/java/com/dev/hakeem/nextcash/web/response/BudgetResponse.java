package com.dev.hakeem.nextcash.web.response;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BudgetResponse {

    private Long id;

    @NotNull(message = "O valor não pode ser nulo")
    @DecimalMin(value = "0.01", message = "O valor deve ser positivo")
    private Double amount;

    @NotNull(message = "A data de início não pode ser nula")
    private Date startDate;

    @NotNull(message = "A data de término não pode ser nula")
    private Date endDate;


}
