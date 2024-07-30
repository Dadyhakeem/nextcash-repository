package com.dev.hakeem.nextcash;

import com.dev.hakeem.nextcash.jwt.jwtToken;
import com.dev.hakeem.nextcash.web.request.UserLoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.util.function.Consumer;

public class JwtAuthentication {


    public  static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String email, String password){
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new UserLoginDTO(email,password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(jwtToken.class)
                .returnResult().getResponseBody().getToken();
        return  httpHeaders -> httpHeaders.add(HttpHeaders.AUTHORIZATION,"Bearer "+ token);
    }
}
