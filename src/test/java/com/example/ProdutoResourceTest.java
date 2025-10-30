package com.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


import com.example.DTO.ProdutoDTO;
import com.example.DTO.ProdutoResponseDTO;
import com.example.service.ProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ProdutoResourceTest {

    @Inject
    ProdutoService produtoService;

    @Test
    void testBuscarPorIdExistente() {
        ProdutoDTO produtoParaCriar = new ProdutoDTO(
                "M16",
                "Colt",
                "fuzil",
                "automatico",
                "Aço",
                1400.0F);
        ProdutoResponseDTO produtoCriada = produtoService.create(produtoParaCriar);
        Long idCriado = produtoCriada.id();

        given()
                .when().get("/produtos/" + idCriado)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        "id", is(idCriado.intValue()),
                        "nome", is("M16"),
                        "fabricante", is("Colt"),
                        "tipo", is("fuzil"),
                        "sistema", is("automatico"),
                        "material", is("Aço"),
                        "preco", is(1400.0F)
                );
    }

    @Test
    void testBuscarPorIdInexistente() {
        given()
                .when().get("/produtos/999")
                .then()
                .statusCode(404);
    }

    @Test
    void testCriar() {
        ProdutoDTO produtoParaCriar = new ProdutoDTO(
                "AK-45",
                "Izmash",
                "fuzil",
                "automatico",
                "Aço",
                1200.0F);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(produtoParaCriar)
                .when().post("/produtos")
                .then()
                .statusCode(201);
    }

    @Test
    void testAlterarComSucesso() {
        ProdutoDTO produtoParaCriar = new ProdutoDTO(
                "AR-15",
                "Colt",
                "fuzil",
                "semi-automatico",
                "Alumínio",
                1200.0F);
        ProdutoResponseDTO produtoCriada = produtoService.create(produtoParaCriar);
        Long idCriado = produtoCriada.id();

        // Define os dados para a alteração
        ProdutoDTO produtoAlterada = new ProdutoDTO(
                "AR-15 Modificado",
                "Colt Defense",
                "fuzil",
                "automatico",
                "Aço Inoxidável",
                1500.0F);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(produtoAlterada)
                .when().put("/produtos/" + idCriado)
                .then()
                .statusCode(204);
    }


    @Test
    void testApagarComSucesso() {
        ProdutoDTO produtoParaCriar = new ProdutoDTO(
                "Glock 19",
                "Glock Ges.m.b.H.",
                "pistola",
                "semi-automatico",
                "Polímero",
                800.0F);
        ProdutoResponseDTO produtoCriada = produtoService.create(produtoParaCriar);
        Long idCriado = produtoCriada.id();

        given()
                .when().delete("/produtos/" + idCriado)
                .then()
                .statusCode(204);
    }
}
