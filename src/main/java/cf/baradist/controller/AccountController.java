package cf.baradist.controller;

import cf.baradist.model.Account;
import cf.baradist.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/user/{userId}/account")
@Api(value = "account", description = "Operations about user accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {
    private AccountService accountService = AccountService.getInstance();

    @GET
    @ApiOperation(value = "Get account by ID",
            notes = "Returns an account by a given ID")
    public Response getAllAccountsByUserId(@PathParam("userId") Long userId) {
        List<Account> accounts = accountService.getAllAccountsByUserId(userId);
        GenericEntity<List<Account>> genericEntity = new GenericEntity<List<Account>>(accounts) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @ApiOperation(value = "Get account by ID",
            notes = "Returns an account by a given ID")
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        if (!account.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(account.get()).build();
    }

    @POST
    @ApiOperation(value = "Creant account",
            notes = "Create a new account")
    public Response add(Account account) {
        return Response.ok(accountService.addAccount(account).get()).build();
    }

    @PUT
    @ApiOperation(value = "Update account",
            notes = "Updates fields of an account with a given ID")
    @Path("{id}")
    public Response update(@PathParam("id") Long accountId, Account account) {
        accountService.updateAccount(accountId, account);
        return Response.ok().build();
    }

    @DELETE
    @ApiOperation(value = "Delete account",
            notes = "Removes an account with a given ID")
    @Path("{id}")
    public Response delete(@PathParam("id") Long accountId) {
        accountService.deleteAccount(accountId);
        return Response.ok().build();
    }
}
