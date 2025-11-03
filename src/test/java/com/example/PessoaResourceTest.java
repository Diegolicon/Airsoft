package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaJuridicaDTO;
import com.example.DTO.PessoaResponseDTO;
import com.example.service.PessoaService;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import java.time.LocalDate;

@QuarkusTest
public class PessoaResourceTest {

    @Inject
    PessoaService pessoaService;

    @Test
    public void buscarTodosTest() {
        RestAssured.given()
                .when()
                .get("/pessoas")
                .then()
                .statusCode(200);
    }

    @Test
    public void incluirPessoaFisicaTest() {
        PessoaFisicaDTO dto = new PessoaFisicaDTO(
                "Nome Teste PF",
                "pf.teste@email.com",
                "11122233344", // 11 dígitos
                LocalDate.of(1990, 1, 15)
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/pessoas/fisica")
                .then()
                .statusCode(201) // 201 Created
                .body("id", CoreMatchers.notNullValue())
                .body("nome", CoreMatchers.is("Nome Teste PF"))
                .body("email", CoreMatchers.is("pf.teste@email.com"))
                .body("cpf", CoreMatchers.is("11122233344"))
                .body("tipoPessoa", CoreMatchers.is("PF"))
                .body("razaoSocial", CoreMatchers.nullValue()); // Garante que campos de PJ são nulos
    }

    @Test
    public void incluirPessoaJuridicaTest() {
        PessoaJuridicaDTO dto = new PessoaJuridicaDTO(
                "Razao Social Teste PJ",
                "pj.teste@email.com",
                "11222333000144"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/pessoas/juridica")
                .then()
                .statusCode(201)
                .body("id", CoreMatchers.notNullValue())
                .body("razaoSocial", CoreMatchers.is("Razao Social Teste PJ"))
                .body("cnpj", CoreMatchers.is("11222333000144"))
                .body("tipoPessoa", CoreMatchers.is("PJ"))
                .body("nome", CoreMatchers.nullValue()); // Garante que campos de PF são nulos
    }

    @Test
    public void alterarPessoaFisicaTest() {

        PessoaFisicaDTO dtoOriginal = new PessoaFisicaDTO(
                "PF Original",
                "original.pf@email.com",
                "55544433322",
                LocalDate.of(1980, 5, 10)
        );
        PessoaResponseDTO responseCriada = pessoaService.createPessoaFisica(dtoOriginal);
        assertNotNull(responseCriada);

        PessoaFisicaDTO dtoUpdate = new PessoaFisicaDTO(
                "PF Alterado",
                "alterado.pf@email.com",
                "66677788899",
                LocalDate.of(1985, 6, 12)
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                .put("/pessoas/fisica/" + responseCriada.id())
                .then()
                .statusCode(204);

        PessoaResponseDTO responseAlterada = pessoaService.getPessoaById(responseCriada.id());
        assertEquals(dtoUpdate.nome(), responseAlterada.nome());
        assertEquals(dtoUpdate.email(), responseAlterada.email());
        assertEquals(dtoUpdate.cpf(), responseAlterada.cpf());
    }

    @Test
    public void apagarPessoaFisicaTest() {

        PessoaFisicaDTO dto = new PessoaFisicaDTO(
                "Pessoa Para Apagar",
                "apagar@email.com",
                "00000000000",
                LocalDate.of(1999, 1, 1)
        );
        PessoaResponseDTO responseCriada = pessoaService.createPessoaFisica(dto);
        Long idParaApagar = responseCriada.id();
        assertNotNull(responseCriada);


        RestAssured.given()
                .when()
                .delete("/pessoas/" + idParaApagar)
                .then()
                .statusCode(204);


        PessoaResponseDTO responseApagada = pessoaService.getPessoaById(idParaApagar);
        assertNull(responseApagada);
}}