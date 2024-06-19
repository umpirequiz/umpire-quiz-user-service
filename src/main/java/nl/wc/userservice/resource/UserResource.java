package nl.wc.userservice.resource;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import nl.wc.userservice.model.User;
import nl.wc.userservice.service.UserService;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Dependent
public class UserResource {

    private UserService userService;
    private int id;

    public UserResource() {
    }

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    public UserResource with(int id) {
        this.id = id;
        return this;
    }

    @DELETE
    public void deleteUser() {
        userService.deleteUser(id);
    }

    @GET
    @Produces(APPLICATION_JSON)
    public User getUser() {
        return userService.getUser(id);
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public User saveUser(User u) {
        return userService.saveUser(u, id);
    }
}
