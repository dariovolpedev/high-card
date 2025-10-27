package it.sara.demo.security.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import it.sara.demo.security.JwtProperties;
import it.sara.demo.security.jwt.JwtClaims;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties props;
    private SecretKey key;

    @PostConstruct
    void init() {
        byte[] bytes = Decoders.BASE64.decode(Objects.requireNonNull(props.secret(), "jwt secret must not be null"));
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(String username, List<String> roles, List<String> policies) {
        Instant now = Instant.now();
        Instant exp = now.plusMillis(props.expirationMillis());

        var builder = Jwts.builder()
                .subject(username)
                .issuer(props.issuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim(JwtClaims.ROLES, roles)
                .claim(JwtClaims.POLICIES, policies);

        if (props.audience() != null && !props.audience().isBlank()) {
            builder.claim(JwtClaims.AUDIENCE, props.audience());
        }

        return builder.signWith(key).compact();
    }

    public Claims parseAndValidate(String token) {
        // Valida firma, exp, nbf, issuer
        var jwt = Jwts.parser()
                .verifyWith(key)
                .requireIssuer(props.issuer())
                .build()
                .parseSignedClaims(token);

        Claims claims = jwt.getPayload();

        // Audience (opzionale ma consigliata)
        if (props.audience() != null && !props.audience().isBlank()) {
            Object aud = claims.get(JwtClaims.AUDIENCE);
            if (!audienceMatches(aud, props.audience())) {
                throw new IllegalArgumentException("Invalid audience");
            }
        }

        // Enforce Policy richiesta
        if (props.requiredPolicy() != null && !props.requiredPolicy().isBlank()) {
            List<String> policies = claims.get(JwtClaims.POLICIES, List.class);
            if (policies == null || policies.stream().noneMatch(p -> p.equalsIgnoreCase(props.requiredPolicy()))) {
                throw new IllegalArgumentException("Required policy not present: " + props.requiredPolicy());
            }
        }

        return claims;
    }

    public String extractUsername(Claims claims) {
        return claims.getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(Claims claims) {
        return (List<String>) claims.getOrDefault(JwtClaims.ROLES, List.of());
    }

    private boolean audienceMatches(Object audClaim, String expected) {
        if (audClaim == null) return false;
        if (audClaim instanceof String s) return expected.equals(s);
        if (audClaim instanceof List<?> list) return list.stream().anyMatch(v -> expected.equals(String.valueOf(v)));
        if (audClaim instanceof Map<?, ?> map) return expected.equals(String.valueOf(map.get("aud")));
        return false;
    }
}