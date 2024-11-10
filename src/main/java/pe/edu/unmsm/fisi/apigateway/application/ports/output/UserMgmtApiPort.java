package pe.edu.unmsm.fisi.apigateway.application.ports.output;

import pe.edu.unmsm.fisi.apigateway.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface UserMgmtApiPort {
    Mono<Void> saveUser(User user);
    Mono<User> getUserById(Long id);
    Mono<Void> deleteUserById(Long id);
    Flux<User> getAllUsers();
    Mono<User> getUserByUsername(String username);
}
