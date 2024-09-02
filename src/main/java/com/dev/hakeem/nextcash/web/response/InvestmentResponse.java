package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.enums.TipoInvestimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class InvestmentResponse {

    private Long id;


    private String name;


    private Double amount;


    private String tipoInvestimento;


    private LocalDate startDate;

    private LocalDate endDate;

}
