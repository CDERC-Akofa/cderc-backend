package com.cderc.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cderc.backend.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
public class JwtService {
    private String secret;

//    private String secret = "cderc_secret_key"; // for local test
    public JwtService(@Value("${JWT_SECRET:${jwt.secret}}") String secret) {
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

    public String generateToken(User user) {
        Long organizationId = user.getOrganization() != null ? user.getOrganization().getId() : null;

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("email", user.getEmail())
                .withClaim("name", user.getName())
                .withClaim("role", user.getRole().name())
                .withClaim("userId", user.getId())
                .withClaim("organizationId", organizationId)
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
    // Token validieren
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername());
    }
}
