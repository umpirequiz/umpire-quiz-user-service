package nl.wc.userservice.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthTest {

    @Test
    void authCreationTest() {
        Auth u = new Auth();
        Auth u2 = new Auth( "u", " ");
        u2.setUsername("peter");
        u.setToken("peter");
        u.setUsername("peter");

        assertThat(u2.getUsername()).isEqualTo("peter");
        assertThat(u.getUsername()).isEqualTo("peter");
        assertThat(u.getToken()).isEqualTo("peter");
    }

}