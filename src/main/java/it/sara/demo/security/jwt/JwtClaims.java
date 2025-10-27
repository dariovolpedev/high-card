package it.sara.demo.security.jwt;

public final class JwtClaims {
    private JwtClaims() {
    }

    public static final String ROLES = "roles";       // List<String>
    public static final String POLICIES = "policies"; // List<String>
    public static final String AUDIENCE = "aud";      // String o List<String>
}