package nl.wc.userservice.resource;

import nl.wc.userservice.model.User;
import nl.wc.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersResourceTest {

    @Mock
    private UserService userServiceMock;

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
}