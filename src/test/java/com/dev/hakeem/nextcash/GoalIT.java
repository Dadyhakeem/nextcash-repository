package com.dev.hakeem.nextcash;

import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.request.GoalRequest;
import com.dev.hakeem.nextcash.web.response.GoalResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/goal/goal-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/goal/goal-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GoalIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public  void criarGoal_ComRecursosValidos_RetonarComStatus201(){
        GoalResponse responseBody = testClient
                .post()
                .uri("/api/v1/goals")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new GoalRequest("viagem Gambie", 25000.00, 236.00, "2024-12-31", 11L))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GoalResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("viagem Gambie");
    }

    @Test
    public void RecuperarDespesa_ComIdExistente_retornarStatus200ok(){
        GoalResponse responseBody = testClient
                .get()
                .uri("/api/v1/goals/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GoalResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Nova meta");

    }

    @Test
    public void RecuperarDespesa_ComIdInexistente_retornarStatus404(){
        ErroMessage responseBody = testClient
                .get()
                .uri("/api/v1/goals/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

    @Test
    public void Deletargoal_ComIdInexistente_retornarStatusNoContent(){
        testClient
                .delete()
                .uri("/api/v1/goals/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void EditarGoal_ComRecursosValidos_retornarComStatus204(){
        GoalResponse responseBody = testClient
                .put()
                .uri("/api/v1/goals/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new GoalRequest("viagem SENEGAL", 25000.00, 236.00, "2024-12-31", 11L))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GoalResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("viagem SENEGAL");
    }

    @Test
    public void EditarGoal_ComidInValidos_retornarComStatus404(){
        ErroMessage responseBody = testClient
                .put()
                .uri("/api/v1/goals/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new GoalRequest("viagem SENEGAL", 25000.00, 236.00, "2024-12-31", 11L))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }
}
