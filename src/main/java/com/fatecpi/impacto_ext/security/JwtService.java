package com.fatecpi.impacto_ext.security;

import com.fatecpi.impacto_ext.core.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET = "mySecretKeyForJWT"; // Use a secure secret in production
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("name", user.getName())
                .withClaim("cpf", user.getCpf())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .sign(algorithm);
    }

    public String getEmailFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            return null; // Invalid token
        }
    }
}
