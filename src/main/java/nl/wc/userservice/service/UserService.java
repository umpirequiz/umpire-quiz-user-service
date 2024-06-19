package nl.wc.userservice.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.wc.userservice.exceptions.UserExistsExpetion;
import nl.wc.userservice.exceptions.UserIdsDontMatchException;
import nl.wc.userservice.model.User;
import nl.wc.userservice.repository.UserRepo;
import nl.wc.userservice.util.PassUtil;
import nl.wc.userservice.util.TokenUtil;

@Dependent
public class UserService {
    private final UserRepo repo;

    private final TokenUtil tokenUtil;
    private static final String PASSWORD_RETURN_VALUE = "*****";

    @Inject
    UserService(UserRepo repo, TokenUtil tokenUtil) {
        this.repo = repo;
        this.tokenUtil = tokenUtil;
    }

    public User createUser(User u) {
        // check if user exists
        if (!repo.findByName(u.getUsername()).isEmpty()) {
            throw new UserExistsExpetion();
        }
        u.setPassword(PassUtil.digest(u.getUsername(), u.getPassword()));
        u = repo.saveUser(u);
        u.setPassword(PASSWORD_RETURN_VALUE);
        return u;
    }

    public User saveUser(User u, int id) {
        if (u.getId() != id) {
            throw new UserIdsDontMatchException("The following ids are not the same: Id: " + id + ", User.Id: " + u.getId());
        }
        u.setPassword(PassUtil.digest(u.getUsername(), u.getPassword()));
        u = repo.saveUser(u);
        u.setPassword(PASSWORD_RETURN_VALUE);
        u.setToken(tokenUtil.issueToken(u));
        return u;
    }

    public User login(User u) {
        u = repo.findByUsernameAndPassword(u.getUsername(), PassUtil.digest(u.getUsername(), u.getPassword()));
        u.setPassword(PASSWORD_RETURN_VALUE);
        u.setToken(tokenUtil.issueToken(u));
        return u;
    }

    public void deleteUser(int id) {
        repo.deleteUser(id);
    }

    public User getUser(int id) {
        return repo.findById(id);
    }

}
