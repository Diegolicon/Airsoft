package com.example;

import static io.restassured.RestAssured.given;
import com.example.DTO.EnderecoDTO;
import com.example.DTO.EnderecoResponseDTO;
import com.example.service.EnderecoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

@QuarkusTest
class EnderecoResourceTest {

    @Inject
    EnderecoService enderecoService;

    private static Long idEnderecoTeste =  null;

    @Test
    void testBuscarTodosEnderecos(){
        given()
                .when().get("/enderecos")
                .then()
                .statusCode(200);
    }

    @Test
    void testIncluirEndereco() {
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Rua 7",
                "22",
                "Apto 501",
                "Centro",
                "Palmas",
                "TO",
                "77004300"
        );
        given()
                .contentType(ContentType.JSON)
                .body(enderecoDTO)
                .when().post("/enderecos")
                .then()
                .statusCode(201);
    }

    @Test
    void testAlterarEndereco() {
        EnderecoDTO enderecoParaCriar = new EnderecoDTO(
                "Rua 10",
                "2222",
                "Apto 301",
                "Sul",
                "Palmas",
                "TO",
                "77204300"
        );
        EnderecoResponseDTO endereco = enderecoService.create(enderecoParaCriar);
        Long idCriado = endereco.id();


        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Rua 71",
                "322",
                "Apto 509",
                "Centro",
                "Araguaina",
                "TO",
                "77004322"
        );
        given()
                .contentType(ContentType.JSON)
                .body(enderecoDTO)
                .pathParam("id", idCriado)
                .when().put("/enderecos/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    void testDeletarEndereco(){
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Rua 91",
                "47",
                "Apto 401",
                "Norte",
                "Palmas",
                "TO",
                "77204300"
        );
        EnderecoResponseDTO criado = enderecoService.create(enderecoDTO);
        idEnderecoTeste = criado.id();

        given()
                .pathParam("id", idEnderecoTeste)
                .when().delete("/enderecos/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    void testBuscarEnderecoInexistente() {
        Long idInexistente = 99999L;

        given()
                .pathParam("id", idInexistente)
                .when().get("/enderecos/{id}")
                .then()
                .statusCode(404);
    }
}