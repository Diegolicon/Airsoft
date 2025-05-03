package com.example.resource;

import com.example.DTO.ProdutoDTO;
import com.example.DTO.ProdutoResponseDTO;
import com.example.service.ProdutoService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @POST
    public Response create(@Valid ProdutoDTO dto) {
        ProdutoResponseDTO produto = produtoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(produto).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ProdutoDTO dto) {
        produtoService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        produtoService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        ProdutoResponseDTO produto = produtoService.findById(id);
        if (produto == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(produto).build();
    }

    @GET
    @Path("/search")
    public Response findByNome(@QueryParam("nome") String nome) {
        List<ProdutoResponseDTO> lista = produtoService.findByNome(nome);
        return Response.ok(lista).build();
    }

    @GET
    public Response findAll() {
        List<ProdutoResponseDTO> lista = produtoService.findAll();
        return Response.ok(lista).build();
    }
}