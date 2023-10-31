package forktex.SoccerManagerBELite.controllers.profile.resources;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProfileUpdateDTO {
    @NotBlank(message = "firstName field cannot be blank!")
    String firstName;

    @NotBlank(message = "lastName field cannot be blank!")
    String lastName;
}
