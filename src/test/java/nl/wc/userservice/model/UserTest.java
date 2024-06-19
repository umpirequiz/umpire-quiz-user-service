package nl.wc.userservice.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void userCreationTest() {
        User u = new User();
        u.setToken("d");
        u.setUsername("peter");
        u.setPassword("peter");
        u.setAdmin(true);
        u.setId((long) 1);

        assertThat(u.getToken()).isEqualTo("d");
        assertThat(u.getUsername()).isEqualTo("peter");
        assertThat(u.getPassword()).isEqualTo("peter");
        assertThat(u.isAdmin()).isTrue();
        assertThat(u.getId()).isEqualTo(1);
    }

}