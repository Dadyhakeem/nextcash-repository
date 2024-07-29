package com.dev.hakeem.nextcash.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotBlank
    @Email(message = "Formato do email esta invalido",regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private  String email;

    @NotBlank
    @Size(min = 6, max = 100, message = "Password must be exactly 6 characters long")
    private String password;
}
