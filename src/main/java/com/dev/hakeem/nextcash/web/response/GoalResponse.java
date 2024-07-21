package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.entity.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class GoalResponse {

    private Long id;

    private String name;

    @NotNull(message = "O valor alvo não pode ser nulo")
    @Positive(message = "O valor alvo deve ser positivo")
    private Double targetAmount;

    @NotNull(message = "O valor atual não pode ser nulo")
    @Positive(message = "O valor atual deve ser positivo")
    private Double currentAmount;

    @NotNull(message = "O prazo não pode ser nulo")
    @Future(message = "O prazo deve ser uma data futura")
    private LocalDate deadline;

    private User user;


}
