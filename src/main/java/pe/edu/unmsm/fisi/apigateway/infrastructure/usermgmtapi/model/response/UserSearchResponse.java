package pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.response;

import lombok.Builder;

@Builder
public record UserSearchResponse(Long id, String username, String password, String email, String name, String lastname) {}
