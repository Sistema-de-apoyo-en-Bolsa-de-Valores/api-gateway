package pe.edu.unmsm.fisi.apigateway.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.unmsm.fisi.apigateway.application.ports.output.UserMgmtApiPort;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    private final UserMgmtApiPort userMgmtApiPort;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userMgmtApiPort.getUserByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("El usuario no fue encontrado.")))
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        getAuthorities(user.getId().toString())));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String id) {
        return Collections.singletonList(new SimpleGrantedAuthority(id));
    }
}
