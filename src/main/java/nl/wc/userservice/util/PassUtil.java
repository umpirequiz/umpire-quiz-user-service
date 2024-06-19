package nl.wc.userservice.util;

import jakarta.enterprise.context.ApplicationScoped;
import nl.wc.userservice.exceptions.NoSuchAlgorithmRuntimeException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@ApplicationScoped
public class PassUtil {
    private String algorithm = "SHA3-512";

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String digest(String username, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            String plaintext = username + ":" + password;
            md.update(plaintext.getBytes(UTF_8));
            return new String(Base64.getEncoder().encode(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmRuntimeException(e);
        }
    }
}
