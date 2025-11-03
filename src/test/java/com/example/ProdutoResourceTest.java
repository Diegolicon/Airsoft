package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.DTO.ProdutoDTO;
import com.example.DTO.ProdutoResponseDTO;
import com.example.service.ProdutoService;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import java.math.BigDecimal;

@QuarkusTest
public class ProdutoResourceTest {

    @Inject
    ProdutoService produtoService;

    @Test
    public void buscarTodosTest() {
        RestAssured.given()
                .when()
                .get("/produtos")
                .then()
                .statusCode(200);
    }

    @Test
    public void incluirProdutoTest() {
        ProdutoDTO dto = new ProdutoDTO(
                "Glock G17",
                "Glock",
                "pistola",
                "semi-automatico",
                "Polimero",
                new BigDecimal("3500.00"),
                20
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/produtos")
                .then()
                .statusCode(201) // Deve passar agora
                .body("id", CoreMatchers.notNullValue())
                .body("nome", CoreMatchers.is("Glock G17"))
                .body("tipo", CoreMatchers.is("pistola"))
                .body("sistema", CoreMatchers.is("semi-automatico"));
    }

    @Test
    public void alterarProdutoTest() {

        ProdutoDTO dtoOriginal = new ProdutoDTO(
                "AK-47", "Kalashnikov",
                "fuzil",
                "automatico",
                "Metal/Madeira",
                new BigDecimal("7000.00"),
                10
        );
        ProdutoResponseDTO produtoCriado = produtoService.create(dtoOriginal);
        assertNotNull(produtoCriado);


        ProdutoDTO dtoUpdate = new ProdutoDTO(
                "M4A1", "Colt",
                "carabina",
                "automatico",
                "Polimero/Metal",
                new BigDecimal("8500.50"),
                25
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                .put("/produtos/" + produtoCriado.id())
                .then()
                .statusCode(204);

        ProdutoResponseDTO produtoAlterado = produtoService.findById(produtoCriado.id());
        assertNotNull(produtoAlterado);
        assertEquals(dtoUpdate.nome(), produtoAlterado.nome());
        assertEquals(dtoUpdate.fabricante(), produtoAlterado.fabricante());
        assertEquals(dtoUpdate.tipo(), produtoAlterado.tipo());
        assertTrue(dtoUpdate.preco().compareTo(produtoAlterado.preco()) == 0);
        assertEquals(dtoUpdate.estoque(), produtoAlterado.estoque());
    }

    @Test
    public void apagarProdutoTest() {
        ProdutoDTO dto = new ProdutoDTO(
                "Remington 870", "Remington",
                "escopeta",
                "spring",
                "Metal",
                new BigDecimal("2800.00"),
                5
        );
        ProdutoResponseDTO produtoCriado = produtoService.create(dto);
        Long idParaApagar = produtoCriado.id();
        assertNotNull(produtoCriado);

        RestAssured.given()
                .when()
                .delete("/produtos/" + idParaApagar)
                .then()
                .statusCode(204);

        ProdutoResponseDTO produtoApagado = produtoService.findById(idParaApagar);
        assertNull(produtoApagado);
    }
}