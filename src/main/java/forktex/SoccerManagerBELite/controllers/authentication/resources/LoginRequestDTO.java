package forktex.SoccerManagerBELite.controllers.authentication.resources;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "email field cannot be blank!")
    private String email;

    @NotBlank(message = "password field cannot be blank!")
    private String password;
}
