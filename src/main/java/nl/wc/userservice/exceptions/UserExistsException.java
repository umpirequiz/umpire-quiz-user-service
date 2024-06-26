package nl.wc.userservice.exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String s) {
        super(s);
    }

}
