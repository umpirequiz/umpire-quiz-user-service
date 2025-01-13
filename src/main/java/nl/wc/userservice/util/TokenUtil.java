package nl.wc.userservice.util;

import com.ibm.websphere.security.jwt.InvalidBuilderException;
import com.ibm.websphere.security.jwt.InvalidClaimException;
import com.ibm.websphere.security.jwt.JwtBuilder;
import com.ibm.websphere.security.jwt.JwtException;
import com.ibm.websphere.security.jwt.KeyException;
import jakarta.enterprise.context.ApplicationScoped;
import nl.wc.userservice.exceptions.TokenException;
import nl.wc.userservice.model.User;

import static com.ibm.websphere.security.jwt.Claims.AUDIENCE;
import static com.ibm.websphere.security.jwt.Claims.EXPIRATION;
import static com.ibm.websphere.security.jwt.Claims.ISSUED_AT;
import static com.ibm.websphere.security.jwt.Claims.SUBJECT;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;

@ApplicationScoped
public class TokenUtil {

    // private Logger logger;

    // @Inject
    // public TokenUtil(Logger logger) {
    //     this.logger = logger;
    // }

    public TokenUtil() {
    }

    public String issueToken(User user) {
        try {
            String s = EnvironmentUtil.getJWTKey();
            // logger.info(s);

            if (user == null || user.getUsername() == null) {
                throw new TokenException("User or username is null");
            }

            String token = JwtBuilder.create()
                    .issuer("UMPIRE-QUIZ")
                    .claim(SUBJECT, "UMPIRE-QUIZ")
                    .claim("upn", user.getUsername())
                    .claim("groups", new String[]{user.isAdmin() ? "ADMIN" : "USER"})
                    .claim(AUDIENCE, "UserService")
                    .claim(ISSUED_AT, now().getEpochSecond())
                    .claim(EXPIRATION, now().plus(30, MINUTES).getEpochSecond())
                    .signWith("HS512", EnvironmentUtil.getJWTKey())
                    .buildJwt()
                    .compact();

            // logger.info("Generated Token: {}", token);

            return token;
        } catch (JwtException | InvalidBuilderException | InvalidClaimException | KeyException e) {
            throw new TokenException(e);
        }
    }
}

