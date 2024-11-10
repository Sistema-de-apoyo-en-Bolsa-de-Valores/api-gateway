package pe.edu.unmsm.fisi.apigateway.application.ports.input;

import pe.edu.unmsm.fisi.apigateway.domain.model.User;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserUseCase {
    Mono<Void> save(User user);

    Mono<User> findByUsername(String username);
}
