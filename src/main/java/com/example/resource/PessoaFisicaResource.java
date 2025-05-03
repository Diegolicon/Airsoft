package com.example.resource;

import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaFisicaResponseDTO;
import com.example.service.PessoaFisicaService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pessoas-fisicas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaFisicaResource {

    @Inject
    PessoaFisicaService pessoaFisicaService;

    @POST
    public Response create(PessoaFisicaDTO pessoaFisicaDTO) {
        PessoaFisicaResponseDTO createdPessoaFisica = pessoaFisicaService.create(pessoaFisicaDTO);
        return Response.status(Response.Status.CREATED).entity(createdPessoaFisica).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PessoaFisicaDTO pessoaFisicaDTO) {
        pessoaFisicaService.update(id, pessoaFisicaDTO);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        pessoaFisicaService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        PessoaFisicaResponseDTO pessoaFisica = pessoaFisicaService.findById(id);
        return pessoaFisica != null ? Response.ok(pessoaFisica).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response getAll() {
        List<PessoaFisicaResponseDTO> pessoasFisicas = pessoaFisicaService.findAll();
        return Response.ok(pessoasFisicas).build();
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response getByCpf(@PathParam("cpf") String cpf) {
        PessoaFisicaResponseDTO pessoaFisica = pessoaFisicaService.findByCpf(cpf);
        return pessoaFisica != null ? Response.ok(pessoaFisica).build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
