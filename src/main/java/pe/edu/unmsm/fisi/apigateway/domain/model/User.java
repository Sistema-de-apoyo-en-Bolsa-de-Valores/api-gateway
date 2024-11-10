package pe.edu.unmsm.fisi.apigateway.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastname;
}
