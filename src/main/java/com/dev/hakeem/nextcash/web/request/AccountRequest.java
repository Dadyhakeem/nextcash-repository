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

    private  Long id;



    @NotNull(message = "Balance cannot be null")
    @Positive(message = "Balance must be positive")
    private Double balance;

    @NotBlank(message = "Financial institution cannot be blank")
    private String financialInstitution;

    @NotNull(message = "Account type cannot be null")
    private AccountType accountType;
    @NotNull
    private Long user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountRequest that = (AccountRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
