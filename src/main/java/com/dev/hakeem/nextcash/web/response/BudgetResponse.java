package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.enums.CategoryExpense;
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


    private Double amount;


    private String startDate;


    private String endDate;


    private String categoryExpense;


}
