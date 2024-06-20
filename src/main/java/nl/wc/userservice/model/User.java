package nl.wc.userservice.model;

import jakarta.persistence.*;
import jdk.jfr.Name;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="User_Accounts")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, unique = true)
    private String username;
    @Column(length = 256, nullable = false)
    private String password;

    private boolean isAdmin;

    @Transient
    private String token;

    public User() {
    }

    public User(Long id, String username, String password) {
        setId(id);
        setUsername(username);
        setPassword(password);
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
