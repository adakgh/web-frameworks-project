package nl.team12.amsterdamevents.aeserver.app.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWToken {
    private static final String JWT_USERNAME_CLAIM = "sub";
    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_ADMIN_CLAIM = "admin";

    private String userName = null;
    private Long userId = null;
    private boolean admin = false;

    // JWT configurations
    @Value("${jwt.issuer:private company}")
    private String issuer;

    @Value("${jwt.pass-phrase}")
    private String passWord;

    @Value("${jwt.expiration-seconds}")
    private int expiration;

    /**
     * Generate a Json Web Token
     * @param id user id (or subject)
     * @param admin is an administrator?
     * @return the token representation
     */
    public String encode(String id, boolean admin) {
        Key key = getKey(passWord);

        return Jwts.builder()
                .claim(Claims.SUBJECT, id) // registered claim
                .claim(JWT_ADMIN_CLAIM, Boolean.toString(admin)) // public claim
                .setIssuer(issuer) // registered claim
                .setIssuedAt(new Date()) // registered claim
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // registered claim
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Get the secret key
     * @param passWord
     * @return secret key
     */
    private Key getKey(String passWord) {
        byte hmacKey[] = passWord.getBytes(StandardCharsets.UTF_8);
        Key key = new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
        return key;
    }

}
