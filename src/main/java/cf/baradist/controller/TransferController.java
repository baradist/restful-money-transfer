package cf.baradist.controller;

import cf.baradist.model.Transfer;
import cf.baradist.service.TransferService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {
    private TransferService transferService = TransferService.getInstance();

    @GET
    public Response getAllTransfers() {
        List<Transfer> transfers = transferService.getAllTransfers();
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/from/{fromAccountId}")
    public Response getTransferByFromAccountId(@PathParam("fromAccountId") Long fromAccountId) {
        List<Transfer> transfers = transferService.getTransferByFromAccountId(fromAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/to/{toAccountId}")
    public Response getTransferByToAccountId(@PathParam("toAccountId") Long toAccountId) {
        List<Transfer> transfers = transferService.getTransferByToAccountId(toAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/from/{fromAccountId}/to/{toAccountId}")
    public Response getTransferByFromAccountIdAndToAccountId(
            @PathParam("fromAccountId") Long fromAccountId, @PathParam("toAccountId") Long toAccountId) {
        List<Transfer> transfers = transferService.getTransferByFromAccountIdAndToAccountId(fromAccountId, toAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @POST
    public Response add(Transfer transfer) {
        return Response.ok(transferService.addTransfer(transfer).get()).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long transferId, Transfer transfer) {
        transferService.updateTransfer(transferId, transfer);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long transferId) {
        transferService.deleteTransfer(transferId);
        return Response.ok().build();
    }
}
