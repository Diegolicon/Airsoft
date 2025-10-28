package com.example;

import com.example.DTO.AcessorioDTO;
import com.example.DTO.AcessorioResponseDTO;
import com.example.service.AcessorioService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AcessorioResourceTest {

    @Inject
    AcessorioService acessorioService;

    private static Long idAcessorioTeste = null; 

    private AcessorioDTO getValidAcessorioDTO(String nome, String tipo, Float preco) {
        return new AcessorioDTO(
                nome,
                "Fabricante Teste",
                tipo,
                "Material Teste",
                preco,
                "Compatibilidade Universal"
        );
    }

    @Test
    void testBuscarTodosAcessorios() {
        acessorioService.create(getValidAcessorioDTO("Teste Listar", "pente", 1.0F)); 

        given()
                .when().get("/acessorios")
                .then()
                .statusCode(200);
    }

    @Test
    void testIncluirAcessorio() {
        AcessorioDTO acessorioDTO = getValidAcessorioDTO(
                "Mira Laser XYZ",
                "mira",
                350.0F
        );
        
        given()
                .contentType(ContentType.JSON)
                .body(acessorioDTO)
                .when().post("/acessorios")
                .then()
                .statusCode(201);
    }

    @Test
    void testAlterarAcessorio() {
        AcessorioDTO dtoInicial = getValidAcessorioDTO("Silenciador Velho", "silenciador", 800.0F);
        AcessorioResponseDTO criado = acessorioService.create(dtoInicial);
        Long idCriado = criado.id();

        AcessorioDTO dtoAlterado = getValidAcessorioDTO(
                "Silenciador Novo Ultra",
                "silenciador",
                1200.0F
        );
        
        given()
                .contentType(ContentType.JSON)
                .body(dtoAlterado)
                .pathParam("id", idCriado)
                .when().put("/acessorios/{id}")
                .then()
                .statusCode(204); 
    }

    @Test
    void testDeletarAcessorio() {
        AcessorioDTO dtoParaDeletar = getValidAcessorioDTO("Kit Rápido", "kit conversao", 200.0F);
        AcessorioResponseDTO criado = acessorioService.create(dtoParaDeletar);
        idAcessorioTeste = criado.id();

        given()
                .pathParam("id", idAcessorioTeste)
                .when().delete("/acessorios/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    void testBuscarAcessorioInexistente() {
        Long idInexistente = 99999L;

        given()
                .pathParam("id", idInexistente)
                .when()
                .get("/acessorios/{id}")
                .then()
                .statusCode(405);
    }
}