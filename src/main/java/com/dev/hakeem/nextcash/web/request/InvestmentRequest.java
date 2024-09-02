package com.dev.hakeem.nextcash.web.request;

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
public class InvestmentRequest {



    @NotBlank(message = "O nome do investimento não deve estar em branco")
    @Size(max = 100, message = "O nome do investimento deve ter no máximo 100 caracteres")
    private String name;

    @NotNull(message = "O valor do investimento não pode ser nulo")
    @Positive(message = "O valor do investimento deve ser positivo")
    private Double amount;

    @NotNull(message = "O tipo de investimento não pode ser nulo")
    private String tipoInvestimento;



    @NotNull(message = "O ID do usuário não pode ser nulo")
    private Long userid;


    private String startDate;

    private String endDate;
}
