package com.example;


import com.example.DTO.PessoaJuridicaDTO;
import com.example.DTO.PessoaJuridicaResponseDTO;
import com.example.service.PessoaJuridicaService;
import jakarta.inject.Inject;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PessoaJuridicaResourceTest {
    @Inject
    PessoaJuridicaService pessoaJuridicaService;

    private static Long idTeste = null;
    @Test
    void testBuscarTodosPJs() {
        given()
                .when().get("/PJs")
                .then()
                .statusCode(404);
    }

    @Test
    void testIncluirPJ() {
        PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO(
                "Junior",
                "Junior@example.com",
                "32193127321345"

        );

        given()
                .contentType(ContentType.JSON)
                .body(pessoaJuridicaDTO)
                .when().post("/PJs")
                .then()
                .statusCode(404);
    }

    @Test
    void testAlterarPJ() {
        PessoaJuridicaDTO PJParaCriar = new PessoaJuridicaDTO(
                "Junior",
                "Junior@example.com",
                "12345678000191"

        );
        PessoaJuridicaResponseDTO PJ = pessoaJuridicaService.create(PJParaCriar);
        Long idCriado = PJ.id();
        PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO(
                "Junior",
                "Junior@example.com",
                "32193127321345"
        );

        given()
                .contentType(ContentType.JSON)
                .body(pessoaJuridicaDTO)
                .pathParam("id", idCriado)
                .when().post("/PJs/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testApagarPJ() {
        PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO(
                "Junior",
                "Junior@example.com",
                "32193127321345"
        );
        PessoaJuridicaResponseDTO PJCriado = pessoaJuridicaService.create(pessoaJuridicaDTO);
        idTeste = PJCriado.id();

        given()
                .pathParam("id", idTeste)
                .when().delete("/PJs/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testBuscarPedidoInexistente() {
        Long idInexistente = 99999L;

        given()
                .pathParam("id", idInexistente)
                .when().get("/PJs/{id}")
                .then()
                .statusCode(404);
    }

}
