package pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.unmsm.fisi.apigateway.application.ports.output.UserMgmtApiPort;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.client.UserMgmtApiClient;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.mapper.UserMgmtApiMapper;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.exceptions.UserNotFoundException;
import pe.edu.unmsm.fisi.apigateway.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserMgmtApiAdapter implements UserMgmtApiPort {
    private final UserMgmtApiClient userMgmtApiClient;
    private final UserMgmtApiMapper userMgmtApiMapper;

    @Override
    public Mono<Void> saveUser(User user) {
        return this.userMgmtApiClient
                .saveUser(this.userMgmtApiMapper.userToUserSaveRequest(user))
                .then();
    }

    @Override
    public Mono<User> getUserById(Long id) {
        return this.userMgmtApiClient
                .getUserById(id)
                .map(this.userMgmtApiMapper::userSearchResponseToUser)
                .onErrorResume(UserNotFoundException.class, e -> Mono.empty());
    }

    @Override
    public Mono<Void> deleteUserById(Long id) {
        return this.userMgmtApiClient
                .deleteUserById(id)
                .then();
    }

    @Override
    public Flux<User> getAllUsers() {
        return this.userMgmtApiClient
                .getUsers(null)
                .flatMapMany(response -> Flux.fromIterable(this.userMgmtApiMapper.userSearchResponseListToUsers(response)));
    }

    @Override
    public Mono<User> getUserByUsername(String username) {
        return this.userMgmtApiClient
                .getUsers(username)
                .flatMapMany(response -> Flux.fromIterable(this.userMgmtApiMapper.userSearchResponseListToUsers(response)))
                .next();
    }
}