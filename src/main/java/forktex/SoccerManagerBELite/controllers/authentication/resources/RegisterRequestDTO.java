package forktex.SoccerManagerBELite.controllers.authentication.resources;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequestDTO {
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "password field cannot be blank!")
    private String password;
}