package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.enums.TransactionType;
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
public class TransactionResponse {

    private Long id;

    @NotNull(message = "O tipo de transação não pode ser nulo")
    private TransactionType transactionType;

    @DecimalMin(value = "0.01", message = "O valor deve ser positivo")
    private Double amount;

    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
    private String description;

    @NotNull(message = "A data não pode ser nula")
    private Date date;
}
