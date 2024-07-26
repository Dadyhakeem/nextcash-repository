package com.dev.hakeem.nextcash.web.request;


import com.dev.hakeem.nextcash.entity.Account;
import com.dev.hakeem.nextcash.entity.Transaction;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TransferencaRequest {


    private Long id;

    @NotNull(message = "O ID da transação não pode ser nulo.")
    @Positive(message = "O ID da transação deve ser um valor positivo.")
    private Long transaction;

    @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres.")
    private String descricao;

    @NotNull(message = "O ID da conta de origem não pode ser nulo.")
    @Positive(message = "O ID da conta de origem deve ser um valor positivo.")
    private Long accountOrigem;

    @NotNull(message = "O ID da conta de destino não pode ser nulo.")
    @Positive(message = "O ID da conta de destino deve ser um valor positivo.")
    private Long accountDestino;

    @NotNull(message = "O valor não pode ser nulo")
    @Min(value = 1, message = "O valor deve ser maior que zero")
    private Double valor;


}
