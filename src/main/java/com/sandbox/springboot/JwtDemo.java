package com.sandbox.springboot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtDemo implements CommandLineRunner {

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.expiration-in-millis}")
    private long jwtExpirationInMillis;

    @Override
    public void run(String... args) throws Exception {
        // Generate a token
        String token = generateToken("testUser");
        System.out.println("Generated Token: " + token);

        // Validate and decode the token
        Jws<Claims> claims = validateToken(token);
        if (claims == null) {
            System.out.println("Token is invalid");
            return;
        }
        String email = (String) claims.getPayload().get("email");
        String subject = (String) claims.getPayload().getSubject();
        long expiration = claims.getPayload().getExpiration().getTime();
        System.out.println("Token Valid: " + (claims != null));
        if (claims != null) {

            System.out.println("Username: " + subject);
            System.out.println("Expiration: " + expiration);
            System.out.println("Expiration: " + email);
        }
    }

    private String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(this.secret.getBytes());
        Map<String, String> claims = new HashMap<>();
        claims.put("email", username + "@mail.com");
        
        return Jwts.builder()
            .claims(claims)
            .claim("role", "ADMIN")
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
            // .encryptWith(key, (AeadAlgorithm) Jwts.SIG.HS256)
            .signWith(key)
            .compact();
    }

    private Jws<Claims> validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(this.secret.getBytes());
        try {
            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return null;
        }
    }

}
