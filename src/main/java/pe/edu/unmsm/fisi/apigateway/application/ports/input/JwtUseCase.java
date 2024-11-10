package pe.edu.unmsm.fisi.apigateway.application.ports.input;

import reactor.core.publisher.Mono;

public interface JwtUseCase {
    Mono<String> createJwtToken(String username, String password);
}
