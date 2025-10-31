package com.example;

import com.example.DTO.ClienteDTO;
import com.example.DTO.ClienteResponseDTO;
import com.example.service.ClienteService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ClienteResourceTest {

    @Inject
    ClienteService clienteService;

    private static Long idClienteTeste =  null;
    @Test
    void testBuscarTodosClientes() {
        given()
                .when().get("/clientes")
                .then()
                .statusCode(200);
    }

    @Test
    void testIncluirCliente() {
        ClienteDTO clienteDTO = new ClienteDTO(
                1500.0,
                true,
                "Cliente importante",
                5L
        );
                given()
                        .contentType(ContentType.JSON)
                        .body(clienteDTO)
                        .when().post("/clientes")
                        .then()
                        .statusCode(500);
    }

    @Test
    void testAlterarCliente() {
        ClienteDTO clienteParaCriar = new ClienteDTO(
                100.0,
                false,
                "Cliente comum",
                1L
        );
        ClienteResponseDTO cliente = clienteService.create(clienteParaCriar);
        Long idCriado = cliente.id();


        ClienteDTO clienteDTO = new ClienteDTO(
                2000.0,
                false,
                "Alterado para cliente comum",
                1L
        );

        given()
                .contentType(ContentType.JSON)
                .body(clienteDTO)
                .pathParam("id", idCriado)
                .when().put("/clientes/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    void testDeletarCliente() {
        ClienteDTO clienteDTO = new ClienteDTO(
                3000.0,
                false,
                "Cliente para exclusão",
                2L
        );
        ClienteResponseDTO criado = clienteService.create(clienteDTO);
        idClienteTeste = criado.id();

        given()
                .pathParam("id", idClienteTeste)
                .when().delete("/clientes/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    void testBuscarClienteInexistente() {
        Long idInexistente = 99999L;

        given()
                .pathParam("id", idInexistente)
                .when().get("/clientes/{id}")
                .then()
                .statusCode(404);
    }
}
