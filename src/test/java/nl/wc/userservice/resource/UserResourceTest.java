package nl.wc.userservice.resource;

import nl.wc.userservice.model.User;
import nl.wc.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserResourceTest {
    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserResource sut;

    @Test
    void delete() {
        doNothing()
                .when(userServiceMock)
                .deleteUser(anyInt());

        sut.deleteUser();

        verify(userServiceMock, times(1)).deleteUser(anyInt());
    }

    @Test
    void findUserById() {
        when(userServiceMock.getUser(anyInt()))
                .thenReturn(new User());

        sut.getUser();

        verify(userServiceMock, times(1)).getUser(anyInt());
    }

    @Test
    void saveUser() {
        User u = new User();
        u.setId((long) 1);
        when(userServiceMock.saveUser(any(User.class), anyInt()))
                .thenReturn(u);

        sut.saveUser(u);

        verify(userServiceMock, times(1)).saveUser(any(User.class), anyInt());
    }

}