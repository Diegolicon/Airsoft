package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.DTO.ItemPedidoDTO;
import com.example.DTO.PedidoDTO;
import com.example.DTO.PedidoResponseDTO;
import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaResponseDTO;
import com.example.DTO.ProdutoDTO;
import com.example.DTO.ProdutoResponseDTO;
import com.example.DTO.UsuarioResponseDTO;
import com.example.service.PedidoService;
import com.example.service.PessoaService;
import com.example.service.ProdutoService;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@QuarkusTest
public class PedidoResourceTest {
    String login = null;

    @Inject
    PedidoService pedidoService;

    @Inject
    PessoaService pessoaService;

    @Inject
    ProdutoService produtoService;

    // Entidades de setup
    private PessoaResponseDTO pessoaTest;
    private ProdutoResponseDTO produtoTest;
    private ProdutoResponseDTO produtoTest2;
    private UsuarioResponseDTO usuariotest;

    @BeforeEach
    @Transactional
    void setUp() {
        //Garante que um Cliente e Produtos existam ---
        PessoaFisicaDTO pDTO = new PessoaFisicaDTO(
                "Cliente Pedido Teste", "cliente.pedido@teste.com",
                "77766655544", LocalDate.of(1990, 1, 1)
        );
        PessoaResponseDTO pExistente = pessoaService.getPessoaByCpf(pDTO.cpf());
        pessoaTest = (pExistente == null) ? pessoaService.createPessoaFisica(pDTO) : pExistente;

        // 2. Cria um Produto 1
        ProdutoDTO prodDTO1 = new ProdutoDTO(
                "Produto Teste 1", "Fabricante A", "Tipo A", "Sistema A",
                "Material A", new BigDecimal("150.00"), 100
        );
        ProdutoResponseDTO prodExistente1 = produtoService.findByNome("Produto Teste 1");
        if (prodExistente1 == null) {
            produtoTest = produtoService.create(prodDTO1);
        } else {
            produtoTest = prodExistente1;
        }

        ProdutoDTO prodDTO2 = new ProdutoDTO(
                "Produto Teste 2", "Fabricante B", "Tipo B", "Sistema B",
                "Material B", new BigDecimal("50.00"), 50
        );
        ProdutoResponseDTO prodExistente2 = produtoService.findByNome("Produto Teste 2");
        if (prodExistente2 == null) {
            produtoTest2 = produtoService.create(prodDTO2);
        } else {
            produtoTest2 = prodExistente2;
        }
    }

    @Test
    public void incluirPedidoTest() {
        ItemPedidoDTO itemDto = new ItemPedidoDTO(produtoTest.id(), 5);
        PedidoDTO pedidoDto = new PedidoDTO(
                pessoaTest.id(),
                Collections.singletonList(itemDto),// Lista com 1 item
                LocalDate.now(),
                "Processando"
        );


        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(pedidoDto)
                .when()
                .post("/pedidos")
                .then()
                .statusCode(201)
                .body("id", CoreMatchers.notNullValue())
                .body("cliente.id", CoreMatchers.is(pessoaTest.id().intValue()))
                .body("itens.size()", CoreMatchers.is(1))
                .body("itens[0].produto.id", CoreMatchers.is(produtoTest.id().intValue()))
                .body("itens[0].quantidade", CoreMatchers.is(5))
                .body("itens[0].preco", CoreMatchers.is(150.00f)); // Verifica o preço salvo
    }

    @Test
    public void alterarPedidoTest() {
        ItemPedidoDTO itemOriginalDto = new ItemPedidoDTO(produtoTest.id(), 2);
        PedidoDTO pedidoOriginalDto = new PedidoDTO(pessoaTest.id(), List.of(itemOriginalDto),LocalDate.now(), login);
        PedidoResponseDTO pedidoCriado = pedidoService.createPedido(pedidoOriginalDto, login);
        assertNotNull(pedidoCriado);

        ItemPedidoDTO itemUpdateDto = new ItemPedidoDTO(produtoTest2.id(), 10);
        PedidoDTO pedidoUpdateDto = new PedidoDTO(
                pessoaTest.id(), // mantem o cliente
                List.of(itemUpdateDto),LocalDate.now(),"PROCESSANDO"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(pedidoUpdateDto)
                .when()
                .put("/pedidos/" + pedidoCriado.id())
                .then()
                .statusCode(204);

        PedidoResponseDTO pedidoAlterado = pedidoService.getPedidoById(pedidoCriado.id());
        assertNotNull(pedidoAlterado);
        assertEquals(1, pedidoAlterado.itens().size());
        assertEquals(produtoTest2.id(), pedidoAlterado.itens().get(0).produto().id());
        assertEquals(10, pedidoAlterado.itens().get(0).quantidade());
    }

    @Test
    public void apagarPedidoTest() {
        ItemPedidoDTO itemDto = new ItemPedidoDTO(produtoTest.id(), 1);
        PedidoDTO pedidoDto = new PedidoDTO(pessoaTest.id(), List.of(itemDto),LocalDate.now(),login);
        PedidoResponseDTO pedidoCriado = pedidoService.createPedido(pedidoDto, login);
        Long idParaApagar = pedidoCriado.id();
        assertNotNull(pedidoCriado);

        RestAssured.given()
                .when()
                .delete("/pedidos/" + idParaApagar)
                .then()
                .statusCode(204);

        org.junit.jupiter.api.Assertions.assertThrows(
            jakarta.ws.rs.NotFoundException.class,
            () -> pedidoService.getPedidoById(idParaApagar)
        );
    }
}