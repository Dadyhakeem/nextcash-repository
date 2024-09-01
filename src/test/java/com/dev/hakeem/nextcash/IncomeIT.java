package com.dev.hakeem.nextcash;

import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.request.IncomeRequest;
import com.dev.hakeem.nextcash.web.response.IncomeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/income/income-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/income/income-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class IncomeIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void CriarReceita_ComRecursosValidos_RetornarComStatus200(){
        IncomeResponse responseBody = testClient
                .post()
                .uri("/api/v1/incomes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new IncomeRequest("SALARIO", 1500.00, "salario do mes", 2L, "2024-09-01"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(IncomeResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();


    }
    @Test
    public void ListarTodosAsReceita_ComRetornoDe200(){
        List<IncomeResponse> responseBody = testClient
                .get()
                .uri("/api/v1/incomes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(IncomeResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

    }

    @Test
    public void RecuperarReceita_ComIdValido_RetornarStatus200(){
        IncomeResponse responseBody = testClient
                .get()
                .uri("/api/v1/incomes/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(IncomeResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getAmount()).isEqualTo(12500);
        org.assertj.core.api.Assertions.assertThat(responseBody.getCategoryIncome()).isEqualTo("SALARIO");
    }

    @Test
    public void RecuperarReceita_ComIdInValido_RetornarStatus404(){
        ErroMessage responseBody = testClient
                .get()
                .uri("/api/v1/incomes/110")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

    @Test
    public void deletarReceita_ComRecursoValidos_RetornarNoContent(){
        testClient
                .delete()
                .uri("/api/v1/incomes/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();



    }

    @Test
    public void deletarReceita_ComRecursoInvalidos_RetornarStatus404(){
        ErroMessage responseBody = testClient
                .delete()
                .uri("/api/v1/incomes/110")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);


    }

    @Test
    public void EditarReceita_ComIdExistente_RetornarComStatus200(){

        // Editando category .
        IncomeResponse responseBody = testClient
                .put()
                .uri("/api/v1/incomes/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new IncomeRequest("VENDAS", 1500.00, "salario do mes", 2L, "2024-08-01"))
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(IncomeResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCategoryIncome()).isEqualTo("VENDAS");

      // Editando category e descricao e horario.
         responseBody = testClient
                .put()
                .uri("/api/v1/incomes/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new IncomeRequest("SALARIO", 1500.00, "salario do mes", 2L, "2024-09-01"))
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(IncomeResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCreated_at()).isEqualTo("2024-09-01");

        // Editando category e descricao .
        responseBody = testClient
                .put()
                .uri("/api/v1/incomes/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new IncomeRequest("VENDAS", 1500.00, "Hora Extras", 2L, "2024-08-01"))
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(IncomeResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getDescricao()).isEqualTo("Hora Extras");




    }

    @Test
    public void EditarReceita_ComIdInexistente_RetornarComStatus404(){

        // Editando category .
        ErroMessage responseBody = testClient
                .put()
                .uri("/api/v1/incomes/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new IncomeRequest("VENDAS", 1500.00, "salario do mes", 2L, "2024-08-01"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

        // Editando category e descricao e horario.
        responseBody = testClient
                .put()
                .uri("/api/v1/incomes/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new IncomeRequest("SALARIO", 1500.00, "salario do mes", 2L, "2024-09-01"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

        // Editando category e descricao .
        responseBody = testClient
                .put()
                .uri("/api/v1/incomes/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new IncomeRequest("VENDAS", 1500.00, "Hora Extras", 2L, "2024-08-01"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);




    }


}
