package com.dev.hakeem.nextcash;

import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.request.AccountRequest;
import com.dev.hakeem.nextcash.web.response.AccountResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/account/account-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/account/account-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AccountIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public  void  criarConta_ComRecursosValidos_retornarComStatus201(){
        AccountResponse responseBody = testClient
                .post()
                .uri("/api/v1/accounts")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new AccountRequest(2000.0, "itau", "CONTA_POUPANCA",11L ))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponse.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getAccountType()).isEqualTo("CONTA_POUPANCA");
        org.assertj.core.api.Assertions.assertThat(responseBody.getFinancialInstitution()).isEqualTo("itau");
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getBalance()).isEqualTo(2000.0);

    }

    @Test
    public  void  criarConta__ComCamposInvalido_RetornarErroMessageComStatus422(){
        ErroMessage responseBody = testClient
                .post()
                .uri("/api/v1/accounts")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new AccountRequest( -2.0, "itau", "CONTA_POUPANCA",11L ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/accounts")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new AccountRequest( 2.0, " ", "CONTA_POUPANCA",11L ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/accounts")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new AccountRequest( 2.0, " ", "",11L ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/accounts")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new AccountRequest( 2.0, "itau", "",11L ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


    }

    @Test
    public  void  criarConta_ComADMIN_retornarErroMessageComStatus403(){
        ErroMessage responseBody = testClient
                .post()
                .uri("/api/v1/accounts")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "amor@gmail.com", "123456"))
                .bodyValue(new AccountRequest(2000.0, "itau", "CONTA_POUPANCA",11L ))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);


    }

    @Test
    public  void recuperarConta_ComIdValido_retornarStatuscode200(){
        AccountResponse responseBody = testClient
                .get()
                .uri("/api/v1/accounts/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponse.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(11);
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

    }

    @Test
    public  void recuperarConta_ComIdInValido_retornarErroMessageComStatuscode404(){
        ErroMessage responseBody = testClient
                .get()
                .uri("/api/v1/accounts/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);


    }


    @Test
    public  void recuperarTodosAsConta_retornarStatuscode200(){
        List<AccountResponse> responseBody =  testClient
                .get()
                .uri("/api/v1/accounts")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AccountResponse.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isGreaterThan(0);
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();


    }


    @Test
    public void deletaConta_ComIdValido_retornarComStatuscode204() {
        testClient
                .delete()
                .uri("/api/v1/accounts/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    public void deletaConta_ComIdinexistente_retornarErroMessageComStatuscode404() {
        ErroMessage responseBody = testClient
                .delete()
                .uri("/api/v1/accounts/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }




}
