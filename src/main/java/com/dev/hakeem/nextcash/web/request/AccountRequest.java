package com.dev.hakeem.nextcash.web.request;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {





    @NotNull(message = "Balance cannot be null")
    @Positive(message = "Balance deve ser  positive")
    private Double balance;

    @NotBlank(message = "Financial institution nao pode ser null")
    private String financialInstitution;

    @NotBlank(message = "Account nao deve ser null")
    private String accountType;

    private Long userId;



}
