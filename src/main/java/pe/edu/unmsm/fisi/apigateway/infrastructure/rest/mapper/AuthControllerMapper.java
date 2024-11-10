package pe.edu.unmsm.fisi.apigateway.infrastructure.rest.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.unmsm.fisi.apigateway.infrastructure.rest.model.request.RegistrationRequestDto;
import pe.edu.unmsm.fisi.apigateway.infrastructure.rest.model.response.AuthenticationResponseDto;
import pe.edu.unmsm.fisi.apigateway.domain.model.User;

@Mapper(componentModel = "spring", builder = @Builder)
public interface AuthControllerMapper {
    @Mapping(target = "id", ignore = true)
    User registrationRequestDtoToUser(RegistrationRequestDto registrationRequestDto);

    AuthenticationResponseDto toAuthenticationResponseDto(String jwt, String username);
}
