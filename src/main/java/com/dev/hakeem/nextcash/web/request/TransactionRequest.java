package com.dev.hakeem.nextcash.web.request;

import com.dev.hakeem.nextcash.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
public class TransactionRequest {

    private Long id;



    @DecimalMin(value = "0.01", message = "O valor deve ser positivo")
    private Double amount;

    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
    private String description;

    @NotNull(message = "A data não pode ser nula")
    private Date date;

    @NotNull(message = "O ID da conta não pode ser nulo")
    private Long accountId;
}
