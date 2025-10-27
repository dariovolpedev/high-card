package it.sara.demo.security;


import it.sara.demo.security.enums.Policy;
import it.sara.demo.security.enums.Role;
import it.sara.demo.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        // Esempio: ruolo base USER, policy richiesta APPLICABLE_AUTHZ
        List<String> roles = List.of(Role.USER.name());
        List<String> policies = List.of(Policy.APPLICABLE_AUTHZ.name());

        String token = jwtService.generateToken(request.username(), roles, policies);
        return ResponseEntity.ok(token);
    }

    public record LoginRequest(String username, String password) {
    }
}