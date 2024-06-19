package nl.wc.userservice.exceptions;

public class UserIdsDontMatchException extends RuntimeException {

    public UserIdsDontMatchException(String s) {
        super(s);
    }
}
