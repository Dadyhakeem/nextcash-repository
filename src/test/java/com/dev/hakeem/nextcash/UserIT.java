package com.dev.hakeem.nextcash;

import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.request.UserCreateDTO;
import com.dev.hakeem.nextcash.web.request.UserUpdatePasswordDTO;
import com.dev.hakeem.nextcash.web.response.UserCreateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/user/user-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/user/user-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUser_ComUsernameEPaswordValidos_RetornaruserCriadoComStatus201() {
        UserCreateDTO createDTO = new UserCreateDTO("dady", "ailton@gmail.com", "123456");

        UserCreateResponse responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserCreateResponse.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("ailton@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("dady");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
    }


    @Test
    public void createUser_ComUsernameInvalidos_RetornarErroMessagestatus422() {


        ErroMessage responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("dady ", "ailton@gmail", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO(" ", "ailton@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("dady ", "ailtongmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("dady ", "@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("dady ", " ", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }


    @Test
    public void createUser_ComPasswordInvalidos_RetornarErroMessagestatus422() {


        ErroMessage responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("dady ", "ailton@gmail.com", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("dady ", "ailton@gmail.com", "123456769"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("dady ", "ailtongmail.com", " "))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }


    @Test
    public void createUser_ComUsernameRepetido_RetornarErroMessageComStatus409() {
        UserCreateDTO createDTO = new UserCreateDTO("dady", "dady@gmail.com", "123456");

        ErroMessage responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void buscarUser_ComIdExistente_RetornarUserComStatus200() {


        UserCreateResponse responseBody = testClient
                .get()
                .uri("/api/v1/users/5")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserCreateResponse.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(5);
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("amor@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("Dady");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");


         responseBody = testClient
                .get()
                .uri("/api/v1/users/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserCreateResponse.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(11);
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("user1@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("Dady");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");



        responseBody = testClient
                .get()
                .uri("/api/v1/users/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"user1@gmail.com","123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserCreateResponse.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(11);
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("user1@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("Dady");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");



    }


    @Test
    public void BuscarUser_CoIdInexistente_RetornarErroMessageComStatus404() {
        UserCreateDTO createDTO = new UserCreateDTO("dady", "ailton@gmail.com", "123456");

        ErroMessage responseBody = testClient
                .get()
                .uri("/api/v1/users/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

    @Test
    public void BuscarUser_ComUserClientBuscandoOutroCliente_RetornarErroMessageComStatus403() {
        UserCreateDTO createDTO = new UserCreateDTO("dady", "ailton@gmail.com", "123456");

        ErroMessage responseBody = testClient
                .get()
                .uri("/api/v1/users/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"user1@gmail.com","123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

    @Test
    public void EditarSenha_ComDadosValidos_RetornarComStatus204() {

        testClient
                .put()
                .uri("/api/v1/users/5")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdatePasswordDTO("123456", "123455", "123455"))
                .exchange()
                .expectStatus().isNoContent();


        testClient
                .put()
                .uri("/api/v1/users/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"user1@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdatePasswordDTO("123456", "123455", "123455"))
                .exchange()
                .expectStatus().isNoContent();


    }

    @Test
    public void EditarSenha_ComUserDifeentes_RetornarErroMessageComStatus403() {
        ErroMessage responseBody = testClient
                .put()
                .uri("/api/v1/users/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdatePasswordDTO("123456", "123455", "123455"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);


        responseBody = testClient
                .put()
                .uri("/api/v1/users/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"user1@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdatePasswordDTO("123456", "123455", "123455"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);


    }

    @Test
    public void EditarSenha_ComCamposInvalido_RetornarErroMessageComStatus422() {
        UserUpdatePasswordDTO updatePasswordDTO = new UserUpdatePasswordDTO("", "", "");
        ErroMessage responseBody = testClient
                .put()
                .uri("/api/v1/users/5")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatePasswordDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .put()
                .uri("/api/v1/users/5")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdatePasswordDTO("12345", "12345", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

/*
        responseBody = testClient
                .put()
                .uri("/api/v1/users/5")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdatePasswordDTO("12345678", "12345678", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);*/

    }


    @Test
    public void EditarSenha_ComSenhasInvalido_RetornarErroMessageComStatus400() {
        UserUpdatePasswordDTO updatePasswordDTO = new UserUpdatePasswordDTO("123456", "123455", "123444");
        ErroMessage responseBody = testClient
                .put()
                .uri("/api/v1/users/5")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatePasswordDTO)
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);


        responseBody = testClient
                .put()
                .uri("/api/v1/users/5")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"amor@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdatePasswordDTO("000000", "123456", "123456"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

    }

    @Test
    public void ListarUser_SemQualquerParametro_RetornarListaDeUserComStatus200() {

        List<UserCreateResponse> responseBody = testClient
                .get()
                .uri("/api/v1/users")

                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserCreateResponse.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);

    }
}