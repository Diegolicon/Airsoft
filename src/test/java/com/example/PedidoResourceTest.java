// package com.example;

// import static io.restassured.RestAssured.given;
// import com.example.DTO.PedidoDTO;
// import com.example.DTO.PedidoResponseDTO;
// import com.example.service.PedidoService;
// import io.quarkus.test.junit.QuarkusTest;
// import io.restassured.http.ContentType;
// import jakarta.inject.Inject;

// import org.junit.jupiter.api.Test;

// @QuarkusTest
// public class PedidoResourceTest {

//     @Inject
//     PedidoService pedidoService;

//     private static Long idPedidoTeste = null;
//     @Test
//     void testBuscarTodosPedidos() {
//         given()
//                 .when().get("/pedidos")
//                 .then()
//                 .statusCode(200);
//     }

//     @Test
//     void testIncluirPedido() {
//         PedidoDTO pedidoDTO = new PedidoDTO(
//                 6000.0,
//                 "a caminho",
//                 "5x AK-47",
//                 5L
//         );

//         given()
//                 .contentType(ContentType.JSON)
//                 .body(pedidoDTO)
//                 .when().post("/pedidos")
//                 .then()
//                 .statusCode(404);
//     }

//     @Test
//     void testAlterarPedido() {
//         final PedidoDTO pedidoDTO = new PedidoDTO(
//                 4200.0,
//                 "a caminho",
//                 1L,
//                 2L);

//         given()
//                 .contentType(ContentType.JSON)
//                 .body(pedidoDTO)
//                 .pathParam("id", idPedidoTeste)
//                 .when().put("/pedidos/{id}")
//                 .then()
//                 .statusCode(404);

//     }

//     @Test
//     void testApagarPedido() {
//         PedidoDTO pedidoDTO = new PedidoDTO(4200.0, "separando", 2L, 1L);
//         PedidoResponseDTO pedidoCriado = pedidoService.createPedido(pedidoDTO);
//         idPedidoTeste = pedidoCriado.id();

//         // Apaga via endpoint REST
//         given()
//                 .pathParam("id", idPedidoTeste)
//                 .when().delete("/pedidos/{id}")
//                 .then()
//                 .statusCode(204);
//     }

//     @Test
//     void testBuscarPedidoInexistente() {
//         Long idInexistente = 99999L;

//         given()
//                 .pathParam("id", idInexistente)
//                 .when().get("/pedidos/{id}")
//                 .then()
//                 .statusCode(404);
//     }
// }
