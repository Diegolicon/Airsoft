package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.DTO.EnderecoDTO;
import com.example.DTO.EnderecoResponseDTO;
import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaResponseDTO;
import com.example.service.EnderecoService;
import com.example.service.PessoaService;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import java.time.LocalDate;

@QuarkusTest
public class EnderecoResourceTest {

    @Inject
    EnderecoService enderecoService;

    @Inject
    PessoaService pessoaService; // Necessário para criar a pessoa

    private PessoaResponseDTO pessoaTest; // Pessoa usada nos testes

    /**
     * Helper: Cria uma PessoaFisica antes de cada teste.
     * Endereços precisam de uma Pessoa para existir.
     */
    @BeforeEach
    @jakarta.transaction.Transactional // Necessário para o 'create' do service
    void setUp() {
        // Limpa (opcional, mas bom) e cria uma pessoa de teste
        // Nota: Este é um setup simples. Em testes maiores,
        // é melhor usar o @TestTransaction

        // Tentativa de criar uma pessoa única para os testes
        // (Testes rodam em paralelo, então é melhor criar uma nova sempre)
        PessoaFisicaDTO dto = new PessoaFisicaDTO(
                "Pessoa Endereco Teste",
                "pessoa.endereco@teste.com",
                "99988877766", // Um CPF único para este teste
                LocalDate.of(1995, 1, 1)
        );

        // Verifica se ela já não existe (caso o drop-and-create falhe)
        PessoaResponseDTO jaExiste = pessoaService.getPessoaByCpf(dto.cpf());
        if (jaExiste == null) {
            pessoaTest = pessoaService.createPessoaFisica(dto);
        } else {
            pessoaTest = jaExiste;
        }
    }

    @Test
    public void incluirEnderecoTest() {
        EnderecoDTO dto = new EnderecoDTO("77006050","Quadra ARNE 12 Avenida LO 4","11","complemento","Bairro","Palmas","TO");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                // Usamos o ID da pessoa criada no setup
                .post("/pessoas/" + pessoaTest.id() + "/enderecos")
                .then()
                .statusCode(201) // 201 Created
                .body("id", CoreMatchers.notNullValue())
                .body("cep", CoreMatchers.is("77006050"))
                .body("logradouro", CoreMatchers.is("Quadra ARNE 12 Avenida LO 4"))
                .body("numero", CoreMatchers.is("11"))
                .body("complemento", CoreMatchers.is("complemento"))
                .body("bairro", CoreMatchers.is("Bairro"))
                .body("cidade", CoreMatchers.is("Palmas"))
                .body("estado", CoreMatchers.is("TO"));
    }

    @Test
    public void alterarEnderecoTest() {
        // 1. Setup: Criar um endereço
        EnderecoDTO dtoOriginal = new EnderecoDTO("77006050","Quadra ARNE 12 Avenida LO 4","11","complemento","Bairro","Palmas","TO");
        EnderecoResponseDTO enderecoCriado = enderecoService.create(pessoaTest.id(),dtoOriginal);
        assertNotNull(enderecoCriado);

        // 2. DTO de Update
        EnderecoDTO dtoUpdate = new EnderecoDTO("77000000","Quadra ARNE 12 Avenida LO 4","00","complemento","Bairro","Araguaina","TO");

        // 3. Executar API
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                // Usamos o endpoint direto de endereço
                .put("/enderecos/" + enderecoCriado.id())
                .then()
                .statusCode(204); // 204 No Content

        // 4. Verificação
        EnderecoResponseDTO enderecoAlterado = enderecoService.findById(enderecoCriado.id());
        assertEquals(dtoUpdate.cep(), enderecoAlterado.cep());
        assertEquals(dtoUpdate.logradouro(), enderecoAlterado.logradouro());
    }

    @Test
    public void apagarEnderecoTest() {
        // 1. Setup: Criar um endereço
        EnderecoDTO dto = new EnderecoDTO("77006050","Quadra ARNE 12 Avenida LO 4","11","complemento","Bairro","Palmas","TO");
        EnderecoResponseDTO enderecoCriado = enderecoService.create(pessoaTest.id(),dto);
        Long idParaApagar = enderecoCriado.id();
        assertNotNull(enderecoCriado);

        // 2. Executar API
        RestAssured.given()
                .when()
                .delete("/enderecos/" + idParaApagar)
                .then()
                .statusCode(204);

        // 3. Verificação (padrão do professor)
        EnderecoResponseDTO enderecoApagado = enderecoService.findById(idParaApagar);
        assertNull(enderecoApagado);
    }
}