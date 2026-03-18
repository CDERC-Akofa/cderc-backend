package com.cderc.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
public class JwtService {
    private String secret;

    public JwtService(@Value("${JWT_SECRET}") String secret) {
        this.secret = secret;
    }
    // Token generieren
    public String generateToken(String email) {

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(Algorithm.HMAC256(secret));
    }
    // Email aus Token extrahieren
    public String extractEmail(String token) {

        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getSubject();
    }
}
