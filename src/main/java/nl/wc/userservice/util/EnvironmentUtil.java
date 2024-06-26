package nl.wc.userservice.util;

public enum EnvironmentUtil {
    ;
    private static final String FRONTEND_URL = "http://localhost:4200";
    private static final String JWT_SHAREDKEY = "mySecret";

    public static String getFrontendUrl() {
        String s = System.getenv("FRONTEND_URL");
        return s == null ? FRONTEND_URL : s;
    }

    public static String getJWTKey() {
        String s = System.getenv("JWT_SHAREDKEY");
        return s == null ? JWT_SHAREDKEY : s;
    }
}
