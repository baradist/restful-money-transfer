package cf.baradist.controller;

import cf.baradist.exception.ApiException;
import cf.baradist.model.Account;
import cf.baradist.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Path("/account")
@Api(value = "account", description = "Operations about user accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {
    private AccountService accountService = AccountService.getInstance();

    @GET
    @Path("/user/{userId}")
    @ApiOperation(value = "Get accounts by User ID",
            notes = "Returns all accounts of a given User")
    public Response getAllAccountsByUserId(@PathParam("userId") Long userId) throws SQLException {
        List<Account> accounts = accountService.getAllAccountsByUserId(userId);
        GenericEntity<List<Account>> genericEntity = new GenericEntity<List<Account>>(accounts) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @ApiOperation(value = "Get account by ID",
            notes = "Returns an account by a given ID")
    @Path("/{accountId}")
    public Response getAccountById(@PathParam("accountId") Long accountId) throws SQLException, ApiException {
        Optional<Account> account = accountService.getAccountById(accountId);
        return Response.ok(account.get()).build();
    }

    @POST
    @ApiOperation(value = "Create account",
            notes = "Create a new account")
    public Response addAccount(Account account) throws SQLException {
        return Response.ok(accountService.addAccount(account).get()).build();
    }

    @PUT
    @ApiOperation(value = "Update account",
            notes = "Updates fields of an account with a given Account ID")
    @Path("/{accountId}")
    public Response updateAccount(@PathParam("accountId") Long accountId, Account account) throws SQLException, ApiException {
        accountService.updateAccount(accountId, account);
        return Response.ok().build();
    }

    @DELETE
    @ApiOperation(value = "Delete account",
            notes = "Removes an account with a given ID")
    @Path("/{accountId}")
    public Response deleteAccount(@PathParam("accountId") Long accountId) throws SQLException, ApiException {
        accountService.deleteAccount(accountId);
        return Response.ok().build();
    }
}
