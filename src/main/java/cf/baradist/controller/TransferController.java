package cf.baradist.controller;

import cf.baradist.model.Transfer;
import cf.baradist.service.TransferService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {
    private TransferService transferService = TransferService.getInstance();

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Optional<Transfer> user = transferService.getById(id);
        if (!user.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user.get()).build();
    }

    @GET
    public Response getAllTransfers() {
        List<Transfer> transfers = transferService.getAllTransfers();
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/from/{fromAccountId}")
    public Response getTransfersByFromAccountId(@PathParam("fromAccountId") Long fromAccountId) {
        List<Transfer> transfers = transferService.getTransfersByFromAccountId(fromAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/to/{toAccountId}")
    public Response getTransfersByToAccountId(@PathParam("toAccountId") Long toAccountId) {
        List<Transfer> transfers = transferService.getTransfersByToAccountId(toAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/from/{fromAccountId}/to/{toAccountId}")
    public Response getTransfersByFromAccountIdAndToAccountId(
            @PathParam("fromAccountId") Long fromAccountId, @PathParam("toAccountId") Long toAccountId) {
        List<Transfer> transfers = transferService.getTransfersByFromAccountIdAndToAccountId(fromAccountId, toAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @POST
    public Response add(Transfer transfer) {
        return Response.ok(transferService.addTransfer(transfer).get()).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long transferId) {
        transferService.rollbackTransfer(transferId);
        return Response.ok().build();
    }
}
