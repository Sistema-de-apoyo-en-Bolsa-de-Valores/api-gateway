package pe.edu.unmsm.fisi.apigateway.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.unmsm.fisi.apigateway.application.ports.input.UserUseCase;
import pe.edu.unmsm.fisi.apigateway.application.ports.output.UserMgmtApiPort;
import pe.edu.unmsm.fisi.apigateway.domain.model.User;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final PasswordEncoder passwordEncoder;
    private final UserMgmtApiPort userMgmtApiPort;

    @Override
    public Mono<Void> save(User user) {
        return Mono.fromCallable(() -> this.passwordEncoder.encode(user.getPassword()))
                .map(encodedPassword -> {
                    user.setPassword(encodedPassword);
                    return user;
                })
                .flatMap(this.userMgmtApiPort::saveUser)
                .then();
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return this.userMgmtApiPort.getUserByUsername(username);
    }
}