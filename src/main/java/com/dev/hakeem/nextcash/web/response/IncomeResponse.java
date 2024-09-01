package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.enums.CategoryIncome;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class IncomeResponse {


    private Long id;



    private String categoryIncome;


    private Double amount;


    private String descricao;

    private LocalDate created_at;
}
