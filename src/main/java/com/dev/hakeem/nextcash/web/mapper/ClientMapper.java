package com.dev.hakeem.nextcash.web.mapper;


import com.dev.hakeem.nextcash.entity.Client;
import com.dev.hakeem.nextcash.repository.ClientRepository;
import com.dev.hakeem.nextcash.web.request.ClientResquest;
import com.dev.hakeem.nextcash.web.response.ClientResp;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

  public static Client toClient(ClientResquest resquest){
      return  new ModelMapper().map(resquest,Client.class);
  }

    public static ClientResp toReponse(Client client){
        return  new ModelMapper().map(client,ClientResp.class);
    }

}
