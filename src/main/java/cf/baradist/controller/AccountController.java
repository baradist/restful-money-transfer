package cf.baradist.controller;

import cf.baradist.model.Account;
import cf.baradist.service.AccountService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/user/{userId}/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {
    private AccountService accountService = AccountService.getInstance();

    @GET
    public Response getAllAccountsByUserId(@PathParam("userId") Long userId) {
        List<Account> accounts = accountService.getAllAccountsByUserId(userId);
        GenericEntity<List<Account>> genericEntity = new GenericEntity<List<Account>>(accounts) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        if (!account.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(account.get()).build();
    }

    @POST
    public Response add(Account account) {
        return Response.ok(accountService.addAccount(account).get()).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long accountId, Account account) {
        accountService.updateAccount(accountId, account);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long accountId) {
        accountService.deleteAccount(accountId);
        return Response.ok().build();
    }
}
