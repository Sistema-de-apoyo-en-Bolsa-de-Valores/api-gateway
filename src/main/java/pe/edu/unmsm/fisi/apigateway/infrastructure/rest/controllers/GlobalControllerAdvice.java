package pe.edu.unmsm.fisi.apigateway.infrastructure.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.edu.unmsm.fisi.apigateway.infrastructure.rest.model.response.GenericResponse;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.exceptions.UserAlreadyRegisteredException;

import java.time.LocalDate;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class})
    protected GenericResponse handleNotFound(AuthenticationException ex) {
        return GenericResponse.builder()
                .timestamp(LocalDate.now())
                .messages(List.of(ex.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyRegisteredException.class)
    protected GenericResponse handleConflict(UserAlreadyRegisteredException ex) {
        return GenericResponse.builder()
                .timestamp(LocalDate.now())
                .messages(ex.getMessages())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    protected GenericResponse handleBadRequest(WebExchangeBindException ex) {
        return GenericResponse.builder()
                .timestamp(LocalDate.now())
                .messages(ex.getBindingResult().getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected GenericResponse handleGenericException(Exception ex) {
        return GenericResponse.builder()
                .timestamp(LocalDate.now())
                .messages(List.of(ex.getMessage()))
                .build();
    }
}