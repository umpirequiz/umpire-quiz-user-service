package nl.wc.userservice.util;

import com.ibm.websphere.security.jwt.*;
import jakarta.enterprise.context.ApplicationScoped;
import nl.wc.userservice.exceptions.TokenException;
import nl.wc.userservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ibm.websphere.security.jwt.Claims.*;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;

@ApplicationScoped
public class TokenUtil {


    private final Logger logger = LoggerFactory.getLogger(TokenUtil.class.getName());

    public String issueToken(User user) {
        try {
            String s = EnvironmentUtil.getJWTKey();
            logger.info(s);

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

            logger.info("Generated Token: {}", token);

            return token;
        } catch (JwtException | InvalidBuilderException | InvalidClaimException | KeyException e) {
            throw new TokenException(e);
        }
    }
}

