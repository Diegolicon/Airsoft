package com.example;


import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaFisicaResponseDTO;
import com.example.service.PessoaFisicaService;
import jakarta.inject.Inject;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PessoaFisicaResourceTest {
    @Inject
    PessoaFisicaService pessoaFisicaService;

    private static Long idTeste = null;
    @Test
    void testBuscarTodosPFs() {
        given()
                .when().get("/PFs")
                .then()
                .statusCode(404);
    }

    @Test
    void testIncluirPF() {
        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Junior",
                "Junior@example.com",
                "32193127321",
                LocalDate.of(2025, 5, 3)

        );

        given()
                .contentType(ContentType.JSON)
                .body(pessoaFisicaDTO)
                .when().post("/PFs")
                .then()
                .statusCode(404);
    }

    @Test
    void testAlterarPF() {
        PessoaFisicaDTO PFParaCriar = new PessoaFisicaDTO(
                "Junior",
                "Junior@example.com",
                "12345678000",
                LocalDate.of(2025, 5, 3)

        );
        PessoaFisicaResponseDTO PF = pessoaFisicaService.create(PFParaCriar);
        Long idCriado = PF.id();
        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Junior",
                "Junior@example.com",
                "32193127321",
                LocalDate.of(2025, 5, 3)
        );

        given()
                .contentType(ContentType.JSON)
                .body(pessoaFisicaDTO)
                .pathParam("id", idCriado)
                .when().post("/PFs/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testApagarPF() {
        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Junior",
                "Junior@example.com",
                "32193127321",
                LocalDate.of(2025, 5, 3)
        );
        PessoaFisicaResponseDTO PFCriado = pessoaFisicaService.create(pessoaFisicaDTO);
        idTeste = PFCriado.id();

        given()
                .pathParam("id", idTeste)
                .when().delete("/PFs/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testBuscarPedidoInexistente() {
        Long idInexistente = 99999L;

        given()
                .pathParam("id", idInexistente)
                .when().get("/PFs/{id}")
                .then()
                .statusCode(404);
    }

}
