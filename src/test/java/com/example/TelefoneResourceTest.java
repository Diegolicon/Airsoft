package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaResponseDTO;
import com.example.DTO.TelefoneDTO;
import com.example.DTO.TelefoneResponseDTO;
import com.example.service.PessoaService;
import com.example.service.TelefoneService;

import jakarta.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import java.time.LocalDate;

@QuarkusTest
public class TelefoneResourceTest {

    @Inject
    TelefoneService telefoneService;

    @Inject
    PessoaService pessoaService;
    private PessoaResponseDTO pessoaTest;

    @BeforeEach
    @Transactional
    void setUp() {
        PessoaFisicaDTO dto = new PessoaFisicaDTO(
                "Pessoa Telefone Teste",
                "pessoa.telefone@teste.com",
                "88877766655",
                LocalDate.of(1996, 1, 1)
        );

        PessoaResponseDTO jaExiste = pessoaService.getPessoaByCpf(dto.cpf());
        if (jaExiste == null) {
            pessoaTest = pessoaService.createPessoaFisica(dto);
        } else {
            pessoaTest = jaExiste;
        }
    }

    @Test
    public void incluirTelefoneTest() {
        TelefoneDTO dto = new TelefoneDTO("63", "987654321", "CELULAR");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/pessoas/" + pessoaTest.id() + "/telefones")
                .then()
                .statusCode(201)
                .body("id", CoreMatchers.notNullValue())
                .body("ddd", CoreMatchers.is("63"))
                .body("numero", CoreMatchers.is("987654321"))
                .body("tipo", CoreMatchers.is("CELULAR"));
    }

    @Test
    public void alterarTelefoneTest() {
        TelefoneDTO dtoOriginal = new TelefoneDTO("11", "111111111", "CASA");
        TelefoneResponseDTO telefoneCriado = telefoneService.create(pessoaTest.id(), dtoOriginal);
        assertNotNull(telefoneCriado);

        TelefoneDTO dtoUpdate = new TelefoneDTO("21", "222222222", "TRABALHO");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                .put("/telefones/" + telefoneCriado.id())
                .then()
                .statusCode(204);

        TelefoneResponseDTO telefoneAlterado = telefoneService.findById(telefoneCriado.id());
        assertEquals(dtoUpdate.ddd(), telefoneAlterado.ddd());
        assertEquals(dtoUpdate.numero(), telefoneAlterado.numero());
        assertEquals(dtoUpdate.tipo(), telefoneAlterado.tipo());
    }

    @Test
    public void apagarTelefoneTest() {
        TelefoneDTO dto = new TelefoneDTO("99", "999999999", "APAGAR");
        TelefoneResponseDTO telefoneCriado = telefoneService.create(pessoaTest.id(), dto);
        Long idParaApagar = telefoneCriado.id();
        assertNotNull(telefoneCriado);

        RestAssured.given()
                .when()
                .delete("/telefones/" + idParaApagar)
                .then()
                .statusCode(204);

        TelefoneResponseDTO telefoneApagado = telefoneService.findById(idParaApagar);
        assertNull(telefoneApagado);
    }
}