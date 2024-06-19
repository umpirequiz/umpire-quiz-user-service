package nl.wc.userservice.util;

import nl.wc.userservice.exceptions.NoSuchAlgorithmRuntimeException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public enum PassUtil {
    ;

    public static String digest(String username, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-512");
            String plaintext = username + ":" + password;
            md.update(plaintext.getBytes(UTF_8));
            return new String(Base64.getEncoder().encode(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmRuntimeException(e);
        }
    }
}
