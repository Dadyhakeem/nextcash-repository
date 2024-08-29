package com.dev.hakeem.nextcash.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Future;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class GoalRequest {




    private String name;
    @NotNull(message = "O valor alvo não pode ser nulo")
    @Positive(message = "O valor alvo deve ser positivo")
    private Double targetAmount;

    @NotNull(message = "O valor atual não pode ser nulo")
    @Positive(message = "O valor atual deve ser positivo")
    private Double currentAmount;

    @NotNull(message = "O prazo não pode ser nulo")

    private String  deadline;

    @NotNull(message = "O usuário não pode ser nulo")
    private Long userid;


}
