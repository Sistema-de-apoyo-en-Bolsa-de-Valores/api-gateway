package pe.edu.unmsm.fisi.apigateway.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Service;
import pe.edu.unmsm.fisi.apigateway.application.ports.input.JwtUseCase;
import pe.edu.unmsm.fisi.apigateway.infrastructure.security.JwtUtil;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtUseCase {
    private final ReactiveAuthenticationManager authenticationManager;
    private final ReactiveUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<String> createJwtToken(String username, String password) {
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .flatMap(auth -> userDetailsService.findByUsername(username))
                .map(jwtUtil::generateToken);
    }
}