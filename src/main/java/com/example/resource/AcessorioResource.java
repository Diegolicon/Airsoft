package com.example.resource;

import com.example.DTO.AcessorioDTO;
import com.example.DTO.AcessorioResponseDTO;
import com.example.service.AcessorioService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;

@Path("/acessorios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AcessorioResource {

    @Inject
    AcessorioService acessorioService;

    @POST
    @Transactional
    public Response create(@Valid AcessorioDTO dto) {
        AcessorioResponseDTO acessorio = acessorioService.create(dto);
        return Response.status(Status.CREATED).entity(acessorio).build();
    }

    @GET
    public Response findAll() {
        List<AcessorioResponseDTO> lista = acessorioService.findAll();
        return Response.ok(lista).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid AcessorioDTO dto) {
        try {
            acessorioService.update(id, dto);
            
            return Response.noContent().build(); 

        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        try {
            acessorioService.delete(id);
            
            return Response.noContent().build();
            
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search/tipo")
    public Response findByTipo(@QueryParam("tipo") String tipo) {
        List<AcessorioResponseDTO> lista = acessorioService.findByTipo(tipo);
        return Response.ok(lista).build();
    }
}