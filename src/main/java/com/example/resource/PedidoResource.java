package com.example.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.example.DTO.PedidoDTO;
import com.example.DTO.PedidoResponseDTO;
import com.example.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @Inject
    JsonWebToken jwt;

    @GET
    public List<PedidoResponseDTO> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @GET
    @Path("/{id}")
    public Response getPedidoById(@PathParam("id") Long id) {
        PedidoResponseDTO pedido = pedidoService.getPedidoById(id);
        
        if (pedido == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(pedido).build();
    }

    @POST
    @Transactional
    public Response createPedido(@Valid PedidoDTO pedidoDTO) {
        String login = jwt.getSubject();
        PedidoResponseDTO createdPedido = pedidoService.createPedido(pedidoDTO, login);
        return Response.status(201).entity(createdPedido).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updatePedido(
            @PathParam("id") Long id,
            @Valid PedidoDTO dto) {

        pedidoService.updatePedido(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletePedido(@PathParam("id") Long id) {
        pedidoService.deletePedido(id);
    }
}
