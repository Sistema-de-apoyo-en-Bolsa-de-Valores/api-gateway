package pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.request.UserSaveRequest;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.response.GenericResponse;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.response.UserSearchResponse;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.exceptions.UserAlreadyRegisteredException;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.exceptions.UserNotFoundException;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.exceptions.GenericException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class UserMgmtApiClient {

    private final WebClient webClient;

    @Autowired
    public UserMgmtApiClient(WebClient userMgmtApiWebClient) {
        this.webClient = userMgmtApiWebClient;
    }

    public Mono<UserSearchResponse> saveUser(UserSaveRequest request) {
        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserSearchResponse.class)
                .onErrorMap(this::handleError);
    }

    public Mono<UserSearchResponse> getUserById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(UserSearchResponse.class)
                .onErrorMap(this::handleError);
    }

    public Mono<Void> deleteUserById(Long id) {
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorMap(this::handleError);
    }

    public Mono<List<UserSearchResponse>> getUsers(String username) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("username", username).build())
                .retrieve()
                .bodyToFlux(UserSearchResponse.class)
                .collectList()
                .onErrorMap(this::handleError);
    }

    private Throwable handleError(Throwable error) {
        if (error instanceof WebClientResponseException ex) {
            HttpStatusCode statusCode = ex.getStatusCode();
            String responseBody = ex.getResponseBodyAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<String> messages = new java.util.ArrayList<>(Collections.emptyList());
            try {
                GenericResponse genericResponse = objectMapper.readValue(responseBody, GenericResponse.class);
                messages.addAll(genericResponse.messages());
            } catch (Exception e) {
                messages.add(responseBody);
            }
            if (statusCode.equals(HttpStatus.NOT_FOUND)) {
                return new UserNotFoundException();
            } else if (statusCode.equals(HttpStatus.CONFLICT)) {
                return new UserAlreadyRegisteredException(messages);
            }
            return new GenericException(messages);
        }
        return new GenericException(List.of(error.getMessage()));
    }
}