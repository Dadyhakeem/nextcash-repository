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

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvestmentResponse {

    private Long id;  // Pode ser nulo para criação, obrigatório para atualização

    @NotBlank(message = "O nome do investimento não deve estar em branco")
    @Size(max = 100, message = "O nome do investimento deve ter no máximo 100 caracteres")
    private String name;

    @NotNull(message = "O valor do investimento não pode ser nulo")
    @Positive(message = "O valor do investimento deve ser positivo")
    private Double amount;

    @NotNull(message = "O tipo de investimento não pode ser nulo")
    private TipoInvestimento tipoInvestimento;


    private Date startDate;

    private Date endDate;

}
