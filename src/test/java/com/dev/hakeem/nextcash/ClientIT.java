package com.dev.hakeem.nextcash;

import com.dev.hakeem.nextcash.web.request.ClientResquest;
import com.dev.hakeem.nextcash.web.response.ClientResp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/client/client-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/client/client-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientIT {


    @Autowired
    WebTestClient testClient;

    @Test
    public  void criarCliente_ComCaamposValidos_retornClienteComStatus201(){
        ClientResp responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user3@gmail.com", "123456"))
                .bodyValue(new ClientResquest("Ciro Gastao", "82044878003"))
                .exchange()
                .expectBody(ClientResp.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Ciro Gastao");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("82044878003");
    }


}
