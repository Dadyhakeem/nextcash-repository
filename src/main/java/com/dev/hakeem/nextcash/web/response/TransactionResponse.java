package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponse {


     private TransactionType tipo;

     private Object detalhes;


}
