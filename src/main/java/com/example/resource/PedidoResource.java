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
    public PedidoResponseDTO getPedidoById(@PathParam("id") Long id) {
        return pedidoService.getPedidoById(id);
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
    public PedidoResponseDTO updatePedido(@PathParam("id") Long id, @Valid PedidoDTO pedidoDTO) {
        return pedidoService.updatePedido(id, pedidoDTO);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletePedido(@PathParam("id") Long id) {
        pedidoService.deletePedido(id);
    }
}
