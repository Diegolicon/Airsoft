package com.example.resource;

import com.example.DTO.ClienteDTO;
import com.example.DTO.ClienteResponseDTO;
import com.example.DTO.EnderecoResponseDTO;
import com.example.DTO.PedidoResponseDTO;
import com.example.service.ClienteService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @POST
    public Response create(ClienteDTO clienteDTO) {
        ClienteResponseDTO clienteCriado = clienteService.create(clienteDTO);
        return Response.status(Response.Status.CREATED).entity(clienteCriado).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ClienteDTO clienteDTO) {
        ClienteResponseDTO atualizado = clienteService.update(id, clienteDTO);
        return atualizado != null
                ? Response.ok(atualizado).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteCliente(@PathParam("id") Long id) {
        clienteService.delete(id);
    }

    @GET
    @Path("/{id}")
    public Response getClienteById(@PathParam("id") Long id) {
        ClienteResponseDTO cliente = clienteService.findById(id);
        return cliente != null ? Response.ok(cliente).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response getAll() {
        List<ClienteResponseDTO> clientes = clienteService.findAll();
        return Response.ok(clientes).build();
    }
}
