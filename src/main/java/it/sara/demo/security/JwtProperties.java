package it.sara.demo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secret,
        String issuer,
        Long expirationMillis,
        String audience,
        String requiredPolicy
) {
}
