package com.project.CheapCheese.config;

import com.project.CheapCheese.models.classes.User;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
    private static final String SECRET = "tQ8Jqn8DnWYPz37u9GyeL7zm7M4nvMzE";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    //for retrieving any information from token we will need the secret key
    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //check if the token has expired
    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public static Boolean validateToken(String token) {
        final String email = extractEmail(token);
        return email != null && !isTokenExpired(token);
    }
    public static String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
