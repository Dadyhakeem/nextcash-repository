package com.dev.hakeem.nextcash.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResp {

    private  Long id;
    private  String name;
    private String cpf;
}
