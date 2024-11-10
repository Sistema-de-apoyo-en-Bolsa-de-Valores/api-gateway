package pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("El usuario no fue encontrado.");
    }
}
