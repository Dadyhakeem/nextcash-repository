package com.dev.hakeem.nextcash.web.request;

import com.dev.hakeem.nextcash.entity.Category;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.CategoryExpense;
import com.dev.hakeem.nextcash.enums.CategoryIncome;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class BudgetRequest {

    private Long id;

    @NotNull(message = "O valor não pode ser nulo")
    @DecimalMin(value = "0.01", message = "O valor deve ser positivo")
    private Double amount;

    @NotNull(message = "A data de início não pode ser nula")
    private Date startDate;

    @NotNull(message = "A data de término não pode ser nula")
    private Date endDate;

    @NotNull(message = "O usuário não pode ser nulo")
    private Long userId;

    @NotNull(message = "CategoryExpense")
    private CategoryExpense categoryExpense;
}
