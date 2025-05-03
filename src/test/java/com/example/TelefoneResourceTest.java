package com.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.DTO.TelefoneDTO;
import com.example.DTO.TelefoneResponseDTO;
import com.example.service.TelefoneService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;


@QuarkusTest
public class TelefoneResourceTest {

    @Inject
    TelefoneService telefoneService;

    private static Long idTelefoneTeste = null;

    @Test
    void testBuscarTodosTelefones() {
        given()
                .when().get("/telefones")
                .then()
                .statusCode(200);
    }

    @Test
    void testIncluirTelefone() {
        TelefoneDTO telefoneDTO = new TelefoneDTO(
                "11",
                "987654321"
        );

        given()
                .contentType(ContentType.JSON)
                .body(telefoneDTO)
                .when().post("/telefones")
                .then()
                .statusCode(201)
                .body(
                        "id", notNullValue(),
                        "ddd", is("11"),
                        "numero", is("987654321")
                );
    }

    @Test
    void testAlterarTelefone() {
        TelefoneDTO telefoneDTO = new TelefoneDTO("22", "999998888");
        TelefoneResponseDTO telefoneCriado = telefoneService.create(telefoneDTO);
        idTelefoneTeste = telefoneCriado.id();

        TelefoneDTO telefoneAlterado = new TelefoneDTO(
                "33",
                "888887777"
        );

        given()
                .contentType(ContentType.JSON)
                .body(telefoneAlterado)
                .pathParam("id", idTelefoneTeste)
                .when().put("/telefones/{id}")
                .then()
                .statusCode(200);

        TelefoneResponseDTO response = telefoneService.findById(idTelefoneTeste);
        org.hamcrest.MatcherAssert.assertThat(response.ddd(), is("33"));
        org.hamcrest.MatcherAssert.assertThat(response.numero(), is("888887777"));
    }

    @Test
    void testApagarTelefone() {
        TelefoneDTO telefoneDTO = new TelefoneDTO("44", "123456789"); // Criar telefone para apagar
        TelefoneResponseDTO telefoneCriado = telefoneService.create(telefoneDTO);
        idTelefoneTeste = telefoneCriado.id();

        given()
                .pathParam("id", idTelefoneTeste)
                .when().delete("/telefones/{id}")
                .then()
                .statusCode(204);

        TelefoneResponseDTO response = telefoneService.findById(idTelefoneTeste);
        assertNull(response);
    }
}
