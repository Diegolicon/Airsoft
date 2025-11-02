// package com.example;
//
// import static io.restassured.RestAssured.given;
//
// import com.example.DTO.*;
// import com.example.service.PedidoService;
// import io.quarkus.test.junit.QuarkusTest;
// import io.restassured.http.ContentType;
// import jakarta.inject.Inject;
//
// import org.junit.jupiter.api.Test;
//
// import java.time.LocalDate;
//
// @QuarkusTest
// public class PedidoResourceTest {
//
//     @Inject
//     PessoaFisicaService pessoaFisicaService;
//
//     @Inject
//     PedidoService pedidoService;
//
//     @Inject
//     ClienteService clienteService;
//
//     private static Long idPedidoTeste = null;
//     @Test
//     void testBuscarTodosPedidos() {
//         given()
//                 .when().get("/pedidos")
//                 .then()
//                 .statusCode(200);
//     }
//
//     @Test
//     void testIncluirPedido() {
//         // 1️⃣ Cria uma pessoa física
//         PessoaFisicaDTO pessoa = new PessoaFisicaDTO(
//                 "Bruno Licon",
//                 "12345678900",
//                 "bruno@email.com",
//                 LocalDate.of(1999, 5, 15)
//         );
//
//         var pessoaCriada = pessoaFisicaService.create(pessoa);
//
//         // 2️⃣ Cria o cliente referenciando a pessoa
//         ClienteDTO clienteDTO = new ClienteDTO(pessoaCriada.id());
//         var clienteCriado = clienteService.create(clienteDTO);
//
//         // 3️⃣ Cria o pedido para o cliente criado
//         PedidoDTO pedidoDTO = new PedidoDTO(
//                 6000.0,
//                 "a caminho",
//                 clienteCriado.id()
//         );
//
//         given()
//                 .contentType(ContentType.JSON)
//                 .body(pedidoDTO)
//                 .when().post("/pedidos")
//                 .then()
//                 .statusCode(201);
//     }
//
//
//     @Test
//     void testAlterarPedido() {
//         final PedidoDTO pedidoDTO = new PedidoDTO(
//                 4200.0,
//                 "a caminho",
//                 1L
//         );
//         given()
//                 .contentType(ContentType.JSON)
//                 .body(pedidoDTO)
//                 .pathParam("id", idPedidoTeste)
//                 .when().put("/pedidos/{id}")
//                 .then()
//                 .statusCode(404);
//
//     }
//
//
//
//
//
//     @Test
//     void testApagarPedido() {
//         PedidoDTO pedidoDTO = new PedidoDTO(4200.0, "separando", 1L);
//         PedidoResponseDTO pedidoCriado = pedidoService.createPedido(pedidoDTO);
//          long idTeste = pedidoCriado.id();
//
//         // Apaga via endpoint REST
//         given()
//                 .pathParam("id", idTeste)
//                 .when().delete("/pedidos/{id}")
//                 .then()
//                 .statusCode(204);
//     }
//
//     @Test
//     void testBuscarPedidoInexistente() {
//         Long idInexistente = 99999L;
//
//         given()
//                 .pathParam("id", idInexistente)
//                 .when().get("/pedidos/{id}")
//                 .then()
//                 .statusCode(404);
//     }
// }
