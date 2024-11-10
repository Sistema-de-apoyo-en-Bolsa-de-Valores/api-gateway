package pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class GenericException extends RuntimeException {
    private final List<String> messages;
}
