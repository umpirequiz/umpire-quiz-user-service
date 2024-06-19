package nl.wc.userservice.resource;

import nl.wc.userservice.model.User;
import nl.wc.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersResourceTest {

    @Mock
    private UserService userServiceMock;

    @Mock
    private UserResource userResourceMock;

    @InjectMocks
    private UsersResource sut;

    @Test
    void register() {
        when(userServiceMock.createUser(any(User.class)))
                .thenReturn(new User());

        sut.register(new User());

        verify(userServiceMock, times(1)).createUser(any(User.class));
    }

    @Test
    void login() {
        when(userServiceMock.login(any(User.class)))
                .thenReturn(new User());

        sut.login(new User());

        verify(userServiceMock, times(1)).login(any(User.class));
    }

    @Test
    void toUserResource() {
        when(userResourceMock.with(anyInt()))
                .thenReturn(userResourceMock);

        assertThat(sut.toUserResource(1))
                .isEqualTo(userResourceMock);
        verify(userResourceMock, times(1))
                .with(anyInt());
    }

    @Test
    void userResource() {
        UserResource uR = userResourceMock.with(1);
        assertThat(sut.toUserResource(1)).isEqualTo(uR);
    }

    @Test
    void users() {
        List<User> users = List.of(new User(1L, "test", "test"));
        when(userServiceMock.getAllUsers())
                .thenReturn(users);

        assertThat(sut.users())
                .isEqualTo(users);
        verify(userServiceMock, times(1))
                .getAllUsers();
    }
}