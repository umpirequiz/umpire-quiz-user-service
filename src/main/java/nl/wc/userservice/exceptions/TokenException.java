package nl.wc.userservice.exceptions;

public class TokenException extends RuntimeException {
    public TokenException(String s) {
        super(s);
    }

    public TokenException(Throwable e) {
        super(e);
    }
}
