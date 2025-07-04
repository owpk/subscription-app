package com.owpk.subscription;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private Duration expirationTime;

    public JwtEntry generateToken(String id) {
        var now = Instant.now();
        var expiry = now.plus(expirationTime);
        var expiresAt = Date.from(expiry);

        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);

        if (secretBytes.length < 32)
            throw new IllegalArgumentException("Secret key must be at least 256 bits (32 bytes)");

        var jwt = Jwts.builder()
                .setSubject(id)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(Keys.hmacShaKeyFor(secretBytes), SignatureAlgorithm.HS256)
                .compact();

        return new JwtEntry(expiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), jwt);
    }

    public static record JwtEntry(LocalDate expiresAt, String jwt) {
    }

    public void validate(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build()
                    .parse(jwt);
        } catch (ExpiredJwtException | MalformedJwtException | IllegalArgumentException e) {

        }
    }
}
