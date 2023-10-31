package forktex.SoccerManagerBELite.controllers.player.resources;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PlayerUpdateDTO {
    @NotBlank(message = "firstName field cannot be blank!")
    String firstName;

    @NotBlank(message = "lastName field cannot be blank!")
    String lastName;

    @NotBlank(message = "country field cannot be blank!")
    String country;
}
