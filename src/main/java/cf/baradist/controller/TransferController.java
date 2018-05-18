package cf.baradist.controller;

import cf.baradist.model.Transfer;
import cf.baradist.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/transfer")
@Api(value = "/transfer", description = "Operations about money transfers")
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {
    private TransferService transferService = TransferService.getInstance();

    @GET
    @ApiOperation(value = "Get money transfer by ID",
            notes = "Returns a money transfer by a given ID")
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Optional<Transfer> user = transferService.getById(id);
        return user.map(t ->
                Response.ok(t).build()).orElseGet(() ->
                Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @ApiOperation(value = "Get all money transfers",
            notes = "Returns the whole list of money transfers")
    public Response getAllTransfers() {
        List<Transfer> transfers = transferService.getAllTransfers();
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @ApiOperation(value = "Get money transfers by FROM accountId",
            notes = "Returns a list of money transfers by a given account ID from that they were sent")
    @Path("/from/{fromAccountId}")
    public Response getTransfersByFromAccountId(@PathParam("fromAccountId") Long fromAccountId) {
        List<Transfer> transfers = transferService.getTransfersByFromAccountId(fromAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @ApiOperation(value = "Get money transfers by TO accountId",
            notes = "Returns a list of money transfers by a given account ID to that they were sent")
    @Path("/to/{toAccountId}")
    public Response getTransfersByToAccountId(@PathParam("toAccountId") Long toAccountId) {
        List<Transfer> transfers = transferService.getTransfersByToAccountId(toAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @ApiOperation(value = "Get money transfers FROM accountId TO accountId",
            notes = "Returns a list of money transfers by a given account IDs from/to that they were sent")
    @Path("/from/{fromAccountId}/to/{toAccountId}")
    public Response getTransfersByFromAccountIdAndToAccountId(
            @PathParam("fromAccountId") Long fromAccountId, @PathParam("toAccountId") Long toAccountId) {
        List<Transfer> transfers = transferService.getTransfersByFromAccountIdAndToAccountId(fromAccountId, toAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @POST
    @ApiOperation(value = "Create money transfer",
            notes = "Make a new money transfer and change balances on accounts")
    public Response add(Transfer transfer) {
        return Response.ok(transferService.addTransfer(transfer).get()).build();
    }

    @DELETE
    @ApiOperation(value = "Delete money transfer by ID",
            notes = "Remove a monty transfer and rollback balances on accounts")
    @Path("{id}")
    public Response delete(@PathParam("id") Long transferId) {
        transferService.rollbackTransfer(transferId);
        return Response.ok().build();
    }
}
