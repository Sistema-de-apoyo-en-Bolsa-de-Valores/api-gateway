package pe.edu.unmsm.fisi.apigateway.infrastructure.rest.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegistrationRequestDto(
        @NotBlank(message = "El nombre de usuario no debe estar vacío.")
        @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres.")
        String username,

        @NotBlank(message = "La contraseña no debe estar vacía.")
        @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+<>?]).*$",
                message = "La contraseña debe incluir al menos un número, una letra mayúscula, una letra minúscula y un carácter especial.")
        String password,

        @NotBlank(message = "El correo no debe estar vacío.")
        @Size(max = 320, message = "El correo no puede exceder los 320 caracteres.")
        @Email(message = "El formato del correo es inválido.")
        String email,

        @NotBlank(message = "El nombre no debe estar vacío.")
        @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
        String name,

        @NotBlank(message = "El apellido no debe estar vacío.")
        @Size(max = 100, message = "El apellido no puede exceder los 100 caracteres.")
        String lastname) {}