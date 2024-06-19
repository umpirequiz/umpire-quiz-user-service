package nl.wc.userservice.service;

import nl.wc.userservice.exceptions.UserIdsDontMatchException;
import nl.wc.userservice.model.User;
import nl.wc.userservice.repository.UserRepo;
import nl.wc.userservice.util.PassUtil;
import nl.wc.userservice.util.TokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepoMock;

    @Mock
    private TokenUtil tokenUtilMock;

    @InjectMocks
    private UserService sut;

    @Test
    void createUser() {
        User u = new User();
        u.setUsername("peter");
        u.setPassword("peter");

        when(userRepoMock.saveUser(any(User.class)))
                .thenReturn(u);
        sut.createUser(u);
        verify(userRepoMock, times(1))
                .saveUser(any());
    }

    @Test
    void saveUserHappyFlow() {
        User u = new User();
        u.setId(1L);

        when(userRepoMock.saveUser(any(User.class)))
                .thenReturn(u);

        sut.saveUser(u, 1);

        verify(userRepoMock, times(1))
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

    @Test
    void login() {
        User u = new User();
        u.setUsername("peter");
        u.setPassword(PassUtil.digest(u.getUsername(), u.getPassword()));

        when(userRepoMock.findByUsernameAndPassword(any(String.class), any(String.class)))
                .thenReturn(u);
        when(tokenUtilMock.issueToken(any(User.class))).thenReturn("jtw-token");

        sut.login(u);
        verify(userRepoMock, times(1)).findByUsernameAndPassword(any(String.class), any(String.class));
        verify(tokenUtilMock, times(1)).issueToken(any(User.class));
    }

    @Test
    void deleteUser() {
        doNothing()
                .when(userRepoMock)
                .deleteUser(anyInt());

        when(userRepoMock.findById(anyInt()))
                .thenReturn(new User());

        sut.deleteUser(1);

        verify(userRepoMock, times(1)).deleteUser(anyInt());
        verify(userRepoMock, times(1)).findById(anyInt());
    }
}