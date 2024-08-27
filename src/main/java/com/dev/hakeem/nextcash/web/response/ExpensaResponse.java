package com.dev.hakeem.nextcash.web.response;


import com.dev.hakeem.nextcash.enums.CategoryExpense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ExpensaResponse {

    private Long id;


    private String categoryExpense;


    private Double amount;


    private String descricao;

    private LocalDate createdAt;
}
