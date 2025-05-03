package com.example.resource;

import com.example.DTO.TelefoneDTO;
import com.example.DTO.TelefoneResponseDTO;
import com.example.service.TelefoneService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

@Path("/telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {

    @Inject
    TelefoneService telefoneService;

    @POST
    public Response create(@Valid TelefoneDTO telefoneDTO) {
        try {
            TelefoneResponseDTO created = telefoneService.create(telefoneDTO);
            return Response.status(Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        TelefoneResponseDTO telefone = telefoneService.findById(id);
        if (telefone == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(telefone).build();
    }

    @GET
    public Response findAll() {
        List<TelefoneResponseDTO> telefones = telefoneService.findAll();
        return Response.ok(telefones).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid TelefoneDTO telefoneDTO) {
        try {
            TelefoneResponseDTO updated = telefoneService.update(id, telefoneDTO);
            return Response.ok(updated).build(); // Retorna 200 com o objeto atualizado
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            telefoneService.delete(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/ddd/{ddd}")
    public Response findByDdd(@PathParam("ddd") String ddd) {
        List<TelefoneResponseDTO> telefones = telefoneService.findByDdd(ddd);
        return Response.ok(telefones).build();
    }
}