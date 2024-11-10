package pe.edu.unmsm.fisi.apigateway.infrastructure.rest.model.response;

import lombok.Builder;

@Builder
public record AuthenticationResponseDto(String jwt, String username) {
}