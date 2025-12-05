package com.example.resource;

import com.example.DTO.EnderecoDTO;
import com.example.DTO.EnderecoResponseDTO;
import com.example.service.EnderecoService;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import com.example.service.PessoaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class EnderecoResource {

    @Inject
    PessoaService pessoaService;

    @Inject
    EnderecoService enderecoService;


    @PUT
    @Path("/{id}") 
    @Transactional
    public Response update(@PathParam("id") Long id, EnderecoDTO enderecoDTO) {
        enderecoService.update(id, enderecoDTO);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        enderecoService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        EnderecoResponseDTO endereco = enderecoService.findById(id);
        return endereco != null ? Response.ok(endereco).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @RolesAllowed({"ADM","USER"})
    public Response getAll() {
        List<EnderecoResponseDTO> enderecos = enderecoService.findAll();
        return Response.ok(enderecos).build();
    }

    @GET
    @Path("/cidade/{cidade}")
    @RolesAllowed("USER")
    public Response getByCidade(@PathParam("cidade") String cidade) {
        List<EnderecoResponseDTO> enderecos = enderecoService.findByCidade(cidade);
        return Response.ok(enderecos).build();
    }

    @GET
    @Path("/cep/{cep}")
    public Response getByCep(@PathParam("cep") String cep) {
        List<EnderecoResponseDTO> endereco = enderecoService.findByCep(cep);
        return endereco != null ? Response.ok(endereco).build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
