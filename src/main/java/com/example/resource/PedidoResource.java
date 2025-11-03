package com.example.resource;

import com.example.DTO.PedidoDTO;
import com.example.DTO.PedidoResponseDTO;
import com.example.service.PedidoService;
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
        PedidoResponseDTO createdPedido = pedidoService.createPedido(pedidoDTO);
        return Response.status(Response.Status.CREATED).entity(createdPedido).build();
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
