package com.dev.hakeem.nextcash;

import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.request.IncomeRequest;
import com.dev.hakeem.nextcash.web.request.InvestmentRequest;
import com.dev.hakeem.nextcash.web.response.InvestmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/investiment/investiment-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/investiment/investiment-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class InvestimentIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void CriarInvestiment_ComRecursosValidos_RetornarComstatus201(){
        InvestmentResponse responseBody = testClient
                .post()
                .uri("/api/v1/investimentos")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new InvestmentRequest("Investimento em Tecnologia", 4500.00, "FUNDOS", 11L, "2024-01-01", "2024-12-31"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(InvestmentResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTipoInvestimento()).isEqualTo("FUNDOS");

    }

    @Test
    public void ListarTodosAsInvestimentos_retornarComStatus200(){
        List<InvestmentResponse> responseBody = testClient
                .get()
                .uri("/api/v1/investimentos")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(InvestmentResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void RecuperarInvestimento_ComIdValidos_RetornarComStatus200(){
        InvestmentResponse responseBody = testClient
                .get()
                .uri("/api/v1/investimentos/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(InvestmentResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTipoInvestimento()).isEqualTo("FUNDOS");

    }
    @Test
    public void RecuperarInvestimento_ComIdInvalidos_RetornarComStatus404(){
        ErroMessage responseBody = testClient
                .get()
                .uri("/api/v1/investimentos/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

    @Test
    public  void DeletarInvestimento_ComIdValido_RetornarStatusNoContent(){
        testClient
                .delete()
                .uri("/api/v1/investimentos/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    public  void DeletarInvestimento_ComIdInvalido_RetornarStatusNoContent(){
        ErroMessage responseBody = testClient
                .delete()
                .uri("/api/v1/investimentos/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);


    }

    @Test
    public void EditarInvestimento_ComRecursoValidos_RetoenarStatus204(){
        // editar o nome
        InvestmentResponse responseBody = testClient
                .put()
                .uri("/api/v1/investimentos/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new InvestmentRequest("Investimento em Educacao", 15000.00, "FUNDOS", 11L, "2024-01-01", "2024-12-01"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(InvestmentResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Investimento em Educacao");

        // Editar as datas
        responseBody = testClient
                .put()
                .uri("/api/v1/investimentos/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new InvestmentRequest("Investimento em Educacao", 15000.00, "FUNDOS", 11L, "2024-07-01", "2024-12-31"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(InvestmentResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStartDate()).isEqualTo("2024-07-01");
        org.assertj.core.api.Assertions.assertThat(responseBody.getEndDate()).isEqualTo("2024-12-31");

        // editar o Tipo de investimento
        responseBody = testClient
                .put()
                .uri("/api/v1/investimentos/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new InvestmentRequest("Investimento em Educacao", 15000.00, "ACOES", 11L, "2024-01-01", "2024-12-01"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(InvestmentResponse.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTipoInvestimento()).isEqualTo("ACOES");
    }

    @Test
    public void EditarInvestimento_ComRecursoInvalidos_RetoenarStatus404(){
        // editar o nome
        ErroMessage responseBody = testClient
                .put()
                .uri("/api/v1/investimentos/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new InvestmentRequest("Investimento em Educacao", 15000.00, "FUNDOS", 11L, "2024-01-01", "2024-12-01"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

        // Editar as datas
        responseBody = testClient
                .put()
                .uri("/api/v1/investimentos/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new InvestmentRequest("Investimento em Educacao", 15000.00, "FUNDOS", 11L, "2024-07-01", "2024-12-31"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);


        // editar o Tipo de investimento
        responseBody = testClient
                .put()
                .uri("/api/v1/investimentos/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "user1@gmail.com", "123456"))
                .bodyValue(new InvestmentRequest("Investimento em Educacao", 15000.00, "ACOES", 11L, "2024-01-01", "2024-12-01"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }
}
