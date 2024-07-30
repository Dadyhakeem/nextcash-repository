package com.dev.hakeem.nextcash.web.request;

import com.dev.hakeem.nextcash.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResquest {



     @NotNull
     @Size(min = 5,max = 100)
    private String name;
    @Size(min = 11,max = 11)
    @CPF
    private String cpf;

    private Long user;
}
