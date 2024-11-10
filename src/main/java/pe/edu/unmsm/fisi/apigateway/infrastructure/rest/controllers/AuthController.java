package pe.edu.unmsm.fisi.apigateway.infrastructure.rest.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.unmsm.fisi.apigateway.infrastructure.rest.mapper.AuthControllerMapper;
import pe.edu.unmsm.fisi.apigateway.infrastructure.rest.model.request.AuthenticationRequestDto;
import pe.edu.unmsm.fisi.apigateway.infrastructure.rest.model.request.RegistrationRequestDto;
import pe.edu.unmsm.fisi.apigateway.infrastructure.rest.model.response.AuthenticationResponseDto;
import pe.edu.unmsm.fisi.apigateway.application.service.JwtService;
import pe.edu.unmsm.fisi.apigateway.application.ports.input.UserUseCase;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final JwtService jwtService;
    private final UserUseCase userUseCase;
    private final AuthControllerMapper mapper;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthenticationResponseDto>> createAuthenticationToken(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return this.jwtService.createJwtToken(authenticationRequestDto.username(), authenticationRequestDto.password())
                .map(jwt -> ResponseEntity.ok(mapper.toAuthenticationResponseDto(jwt, authenticationRequestDto.username())));
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> registerUser(@RequestBody @Valid RegistrationRequestDto request) {
        return this.userUseCase.save(mapper.registrationRequestDtoToUser(request))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado de forma exitosa."));
    }
}
