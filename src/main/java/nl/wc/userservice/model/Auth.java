package nl.wc.userservice.model;

import java.util.List;

public class Auth {
    private String username;
    private String token;


    public Auth() {
    }

    public Auth(String username, String token) {
        setUsername(username);
        setToken(token);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
