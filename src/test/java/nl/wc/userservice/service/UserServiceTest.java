package nl.wc.userservice.service;

import nl.wc.userservice.dao.UserDao;
import nl.wc.userservice.exceptions.UserExistsException;
import nl.wc.userservice.exceptions.UserIdsDontMatchException;
import nl.wc.userservice.model.LoginRequest;
import nl.wc.userservice.model.User;
import nl.wc.userservice.util.PassUtil;
import nl.wc.userservice.util.TokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDaoMock;

    @Mock
    private TokenUtil tokenUtilMock;

    @Mock
    private PassUtil passUtilMock;

    @InjectMocks
    private UserService sut;

    @Test
    void createUserHappyFlow() {
        User u = new User();
        u.setUsername("peter");
        u.setPassword("peter");
        when(userDaoMock.userExists(anyString()))
                .thenReturn(false);

        when(userDaoMock.saveUser(any(User.class)))
                .thenReturn(u);

        sut.createUser(u);

        verify(userDaoMock, times(1))
                .userExists(anyString());
        verify(userDaoMock, times(1))
                .saveUser(any());
    }

    @Test
    void createUserExistingUsernameThrowsUserExistsException() {
        User u = new User();
        u.setUsername("test");

        when(userDaoMock.userExists(anyString()))
                .thenReturn(true);

        assertThatThrownBy(() -> sut.createUser(u))
                .isInstanceOf(UserExistsException.class)
                .hasMessage("The username test, is already taken");

        verify(userDaoMock, times(1))
                .userExists(anyString());
    }

    @Test
    void saveUserHappyFlow() {
        User u = new User();
        u.setId(1L);

        when(userDaoMock.saveUser(any(User.class)))
                .thenReturn(u);

        sut.saveUser(u, 1);

        verify(userDaoMock, times(1))
                .saveUser(any(User.class));
    }

    @Test
    void saveUserThrowsUserIdsDontMatchException() {
        User u = new User();
        u.setId(1L);

        assertThatThrownBy(() -> sut.saveUser(u, 2))
                .isInstanceOf(UserIdsDontMatchException.class)
                .hasMessage("The following ids are not the same: Id: 2, User.Id: 1");
    }

//    @Test
//    void login() {
//        when(passUtilMock.digest(anyString(), anyString()))
//                .thenReturn("hashed_password");
//        User u = new User(1L, "peter", "peter");
//        u.setPassword(passUtilMock.digest(u.getUsername(), u.getPassword()));
//
//        when(userDaoMock.findByUsernameAndPassword(any(String.class), any(String.class)))
//                .thenReturn(true);
//        when(tokenUtilMock.issueToken(any(User.class)))
//                .thenReturn("jtw-token");
//
//        sut.login(new LoginRequest());
//        verify(userDaoMock, times(1))
//                .findByUsernameAndPassword(any(String.class), any(String.class));
//        verify(tokenUtilMock, times(1))
//                .issueToken(any(User.class));
//    }

    @Test
    void deleteUser() {
        doNothing()
                .when(userDaoMock)
                .deleteUser(anyInt());

        sut.deleteUser(1);

        verify(userDaoMock, times(1))
                .deleteUser(anyInt());
    }

    @Test
    void getUser() {
        User u = new User();
        when(userDaoMock.findById(anyInt()))
                .thenReturn(u);

        assertThat(sut.getUser(1))
                .isEqualTo(u);


    }

    @Test
    void getAllUsers() {
        List<User> users = List.of(new User(1L, "peter", "peter"));
        when(userDaoMock.getAllUsers())
                .thenReturn(users);

        assertThat(sut.getAllUsers()).isEqualTo(users);
    }
}