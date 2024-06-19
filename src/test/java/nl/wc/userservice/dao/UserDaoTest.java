package nl.wc.userservice.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import nl.wc.userservice.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDaoTest {
    @Mock
    private EntityManager emMock;

    @Mock
    private TypedQuery<User> queryMock;
    @InjectMocks
    private UserDao sut;

    @Test
    void saveUser() {
        when(emMock.merge(any(User.class)))
                .thenReturn(new User());

        sut.saveUser(new User());
        verify(emMock, times(1))
                .merge(any(User.class));
    }

    @Test
    void deleteUser() {
        User u = new User();

        when(emMock.createQuery(any(), eq(User.class)))
                .thenReturn(queryMock);
        when(queryMock.setParameter(eq("id"), any()))
                .thenReturn(queryMock);
        when(queryMock.getSingleResult())
                .thenReturn(u);
        doNothing()
                .when(emMock)
                .remove(any());

        sut.deleteUser(1);

        verify(emMock, times(1))
                .createQuery(any(), eq(User.class));
        verify(queryMock, times(1))
                .setParameter(eq("id"), any());
        verify(queryMock, times(1))
                .getSingleResult();
        verify(emMock, times(1))
                .remove(any());
    }

    @Test
    void findById() {
        when(emMock.createQuery(any(), eq(User.class)))
                .thenReturn(queryMock);
        when(queryMock.setParameter(eq("id"), any()))
                .thenReturn(queryMock);
        when(queryMock.getSingleResult())
                .thenReturn(new User());

        sut.findById(1);

        verify(emMock, times(1))
                .createQuery(any(), eq(User.class));
        verify(queryMock, times(1))
                .setParameter(eq("id"), any());
        verify(queryMock, times(1))
                .getSingleResult();
    }

    @Test
    void findByName() {
        when(emMock.createQuery(any(), eq(User.class)))
                .thenReturn(queryMock);
        when(queryMock.setParameter(eq("s"), any()))
                .thenReturn(queryMock);
        when(queryMock.getResultList())
                .thenReturn(new ArrayList<User>());

        sut.findByName("peter");

        verify(emMock, times(1))
                .createQuery(any(), eq(User.class));
        verify(queryMock, times(1))
                .setParameter(eq("s"), any());
        verify(queryMock, times(1))
                .getResultList();
    }

    @Test
    void findByUsernameAndPassword() {
        when(emMock.createQuery(any(), eq(User.class)))
                .thenReturn(queryMock);
        when(queryMock.setParameter(eq("username"), any()))
                .thenReturn(queryMock);
        when(queryMock.setParameter(eq("password"), any()))
                .thenReturn(queryMock);
        when(queryMock.getSingleResult())
                .thenReturn(new User());

        sut.findByUsernameAndPassword("peter", "peter");

        verify(emMock, times(1))
                .createQuery(any(), eq(User.class));
        verify(queryMock, times(1))
                .setParameter(eq("username"), any());
        verify(queryMock, times(1))
                .setParameter(eq("password"), any());
        verify(queryMock, times(1))
                .getSingleResult();
    }

    @Test
    void userExistsTrue() {
        when(emMock.createQuery(any(), eq(User.class)))
                .thenReturn(queryMock);
        when(queryMock.setParameter(eq("u"), any()))
                .thenReturn(queryMock);
        when(queryMock.getResultList())
                .thenReturn(List.of(new User()));

        assertThat(sut.userExists("test"))
                .isTrue();
        verify(emMock, times(1))
                .createQuery(any(), eq(User.class));
        verify(queryMock, times(1))
                .setParameter(eq("u"), any());
        verify(queryMock, times(1))
                .getResultList();

    }

    @Test
    void userExistsFalse() {
        when(emMock.createQuery(any(), eq(User.class)))
                .thenReturn(queryMock);
        when(queryMock.setParameter(eq("u"), any()))
                .thenReturn(queryMock);
        when(queryMock.getResultList())
                .thenReturn(List.of());

        assertThat(sut.userExists("test"))
                .isFalse();
        verify(emMock, times(1))
                .createQuery(any(), eq(User.class));
        verify(queryMock, times(1))
                .setParameter(eq("u"), any());
        verify(queryMock, times(1))
                .getResultList();
    }

    @Test
    void getAllUsers() {
        List<User> users = List.of(new User(1L, "peter", "peter"), new User(1L, "karel", "karel"));
        when(emMock.createQuery(any(), eq(User.class)))
                .thenReturn(queryMock);
        when(queryMock.getResultList())
                .thenReturn(users);

        assertThat(sut.getAllUsers())
                .isEqualTo(users);
        verify(emMock, times(1))
                .createQuery(any(), eq(User.class));
        verify(queryMock, times(1))
                .getResultList();
    }
}