package com.example.resource;

import com.example.DTO.PessoaJuridicaDTO;
import com.example.DTO.PessoaJuridicaResponseDTO;
import com.example.service.PessoaJuridicaService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pessoas-juridicas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaJuridicaResource {

    @Inject
    PessoaJuridicaService pessoaJuridicaService;

    @POST
    public Response create(PessoaJuridicaDTO pessoaJuridicaDTO) {
        PessoaJuridicaResponseDTO createdPessoaJuridica = pessoaJuridicaService.create(pessoaJuridicaDTO);
        return Response.status(Response.Status.CREATED).entity(createdPessoaJuridica).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PessoaJuridicaDTO pessoaJuridicaDTO) {
        pessoaJuridicaService.update(id, pessoaJuridicaDTO);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        pessoaJuridicaService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        PessoaJuridicaResponseDTO pessoaJuridica = pessoaJuridicaService.findById(id);
        return pessoaJuridica != null ? Response.ok(pessoaJuridica).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response getAll() {
        List<PessoaJuridicaResponseDTO> pessoasJuridicas = pessoaJuridicaService.findAll();
        return Response.ok(pessoasJuridicas).build();
    }

    @GET
    @Path("/cnpj/{cnpj}")
    public Response getByCnpj(@PathParam("cnpj") String cnpj) {
        PessoaJuridicaResponseDTO pessoaJuridica = pessoaJuridicaService.findByCnpj(cnpj);
        return pessoaJuridica != null ? Response.ok(pessoaJuridica).build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
