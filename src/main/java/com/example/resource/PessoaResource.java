package com.example.resource; // ou seu pacote de resource

import com.example.DTO.*;
// O DTO de resposta não é mais retornado, mas é usado no POST
import com.example.service.EnderecoService;
import com.example.service.PessoaService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status; // Importar o Status

@Path("/pessoas")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource {

    @Inject
    PessoaService pessoaService;

    @POST
    @Path("/fisica")
    @Transactional
    public Response createPessoaFisica(@Valid PessoaFisicaDTO dto) {
        return Response.status(Status.CREATED)
                .entity(pessoaService.createPessoaFisica(dto))
                .build();
    }

    @POST
    @Path("/juridica")
    @Transactional
    public Response createPessoaJuridica(@Valid PessoaJuridicaDTO dto) {
        return Response.status(Status.CREATED)
                .entity(pessoaService.createPessoaJuridica(dto))
                .build();
    }


    @GET
    public Response getAllPessoas() {
        return Response.ok(pessoaService.getAllPessoas()).build();
    }

    @GET
    @Path("/{id}")
    public Response getPessoaById(@PathParam("id") Long id) {
        return Response.ok(pessoaService.getPessoaById(id)).build();
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response getPessoaByCpf(@PathParam("cpf") String cpf) {
        return Response.ok(pessoaService.getPessoaByCpf(cpf)).build();
    }

    @GET
    @Path("/cnpj/{cnpj}")
    public Response getPessoaByCnpj(@PathParam("cnpj") String cnpj) {
        return Response.ok(pessoaService.getPessoaByCnpj(cnpj)).build();
    }

    @PUT
    @Path("/fisica/{id}")
    @Transactional
    public Response updatePessoaFisica(
            @PathParam("id") Long id,
            @Valid PessoaFisicaDTO dto) {

        pessoaService.updatePessoaFisica(id, dto);
        return Response.noContent().build();
    }

    @PUT
    @Path("/juridica/{id}")
    @Transactional
    public Response updatePessoaJuridica(
            @PathParam("id") Long id,
            @Valid PessoaJuridicaDTO dto) {

        pessoaService.updatePessoaJuridica(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePessoa(@PathParam("id") Long id) {
        pessoaService.deletePessoa(id);
        return Response.noContent().build();
    }

    @Inject
    EnderecoService enderecoService;

    @POST
    @Path("/{id}/enderecos") // Endpoint: POST /pessoas/1/enderecos
    @Transactional
    public Response addEndereco(
            @PathParam("id") Long pessoaId,
            @Valid EnderecoDTO dto) {

        EnderecoResponseDTO responseDTO = enderecoService.create(pessoaId, dto);

        return Response.status(Status.CREATED)
                .entity(responseDTO)
                .build();
    }

}