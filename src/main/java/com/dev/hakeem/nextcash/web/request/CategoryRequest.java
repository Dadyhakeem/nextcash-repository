package com.dev.hakeem.nextcash.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    private String name;

    @NotBlank(message = "A descrição não pode ser vazia")
    private String description;

    @NotNull(message = "O usuário não pode ser nulo")
    private Long userid;
}
