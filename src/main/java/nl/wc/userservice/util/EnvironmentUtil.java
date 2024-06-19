package nl.wc.userservice.util;

public enum EnvironmentUtil {
    ;

    public static String getJWTKey() {
        String s = System.getenv("JWT_SHAREDKEY");
        return s == null ? "mySecret" : s;
    }
}
