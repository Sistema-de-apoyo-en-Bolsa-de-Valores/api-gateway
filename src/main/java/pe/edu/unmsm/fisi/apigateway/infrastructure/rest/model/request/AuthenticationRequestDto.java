package pe.edu.unmsm.fisi.apigateway.infrastructure.rest.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthenticationRequestDto(
        @NotBlank(message = "El nombre de usuario no debe estar vacío.")
        String username,
        @NotBlank(message = "La contraseña no debe estar vacía.")
        String password
) {}
