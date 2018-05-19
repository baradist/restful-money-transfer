package cf.baradist.controller;

import cf.baradist.exception.ApiException;
import cf.baradist.model.Transfer;
import cf.baradist.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/transfer")
@Api(value = "transfer", description = "Operations about money transfers")
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {
    private TransferService transferService = TransferService.getInstance();

    @GET
    @ApiOperation(value = "Get money transfer by ID",
            notes = "Returns a money transfer by a given ID")
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) throws SQLException, ApiException {
        return Response.ok(transferService.getById(id).get()).build();
    }

    @GET
    @ApiOperation(value = "Get all money transfers",
            notes = "Returns the whole list of money transfers")
    public Response getAllTransfers() throws SQLException {
        List<Transfer> transfers = transferService.getAllTransfers();
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @ApiOperation(value = "Get money transfers by FROM accountId",
            notes = "Returns a list of money transfers by a given account ID from that they were sent")
    @Path("/from/{fromAccountId}")
    public Response getTransfersByFromAccountId(@PathParam("fromAccountId") Long fromAccountId) throws SQLException {
        List<Transfer> transfers = transferService.getTransfersByFromAccountId(fromAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @ApiOperation(value = "Get money transfers by TO accountId",
            notes = "Returns a list of money transfers by a given account ID to that they were sent")
    @Path("/to/{toAccountId}")
    public Response getTransfersByToAccountId(@PathParam("toAccountId") Long toAccountId) throws SQLException {
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
            @PathParam("fromAccountId") Long fromAccountId,
            @PathParam("toAccountId") Long toAccountId)
            throws SQLException {
        List<Transfer> transfers = transferService.getTransfersByFromAccountIdAndToAccountId(fromAccountId, toAccountId);
        GenericEntity<List<Transfer>> genericEntity = new GenericEntity<List<Transfer>>(transfers) {
        };
        return Response.ok(genericEntity).build();
    }

    @POST
    @ApiOperation(value = "Create money transfer",
            notes = "Make a new money transfer and change balances on accounts")
    public Response add(Transfer transfer) throws ApiException, SQLException {
        return Response.ok(transferService.addTransfer(transfer).get()).build();
    }

    @DELETE
    @ApiOperation(value = "Delete money transfer by ID",
            notes = "Remove a monty transfer and rollback balances on accounts")
    @Path("{id}")
    public Response delete(@PathParam("id") Long transferId) throws ApiException {
        transferService.rollbackTransfer(transferId);
        return Response.ok().build();
    }
}
