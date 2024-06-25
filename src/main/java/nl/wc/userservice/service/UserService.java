package nl.wc.userservice.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.wc.userservice.dao.UserDao;
import nl.wc.userservice.exceptions.UserExistsException;
import nl.wc.userservice.exceptions.UserIdsDontMatchException;
import nl.wc.userservice.model.Auth;
import nl.wc.userservice.model.User;
import nl.wc.userservice.util.PassUtil;
import nl.wc.userservice.util.TokenUtil;

import java.util.List;

@Dependent
public class UserService {
    private final UserDao dao;

    private final TokenUtil tokenUtil;
    private final PassUtil passUtil;
    private static final String PASSWORD_RETURN_VALUE = "*****";

    @Inject
    UserService(UserDao dao, TokenUtil tokenUtil, PassUtil passUtil) {
        this.dao = dao;
        this.tokenUtil = tokenUtil;
        this.passUtil = passUtil;
    }

    public User createUser(User u) {
        // check if user exists
        if (dao.userExists(u.getUsername())) {
            throw new UserExistsException("The username " + u.getUsername() + ", is already taken");
        }
        u.setPassword(passUtil.digest(u.getUsername(), u.getPassword()));
        u = dao.saveUser(u);
        u.setPassword(PASSWORD_RETURN_VALUE);
        return u;
    }

    public User saveUser(User u, int id) {
        if (u.getId() != id) {
            throw new UserIdsDontMatchException("The following ids are not the same: Id: " + id + ", User.Id: " + u.getId());
        }
        u.setPassword(passUtil.digest(u.getUsername(), u.getPassword()));
        u = dao.saveUser(u);
        u.setPassword(PASSWORD_RETURN_VALUE);
        return u;
    }

    public Auth login(String username, String password) {
        User u = dao.findByUsernameAndPassword(username, passUtil.digest(username, password));
        if (u == null) {
            return null;
        }
        return new Auth(username,tokenUtil.issueToken(u));
    }

    public void deleteUser(int id) {
        dao.deleteUser(id);
    }

    public User getUser(int id) {
        return dao.findById(id);
    }

    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }
}
