package pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.request;

import lombok.Builder;

@Builder
public record UserSaveRequest(
        String username,
        String password,
        String email,
        String name,
        String lastname) {}