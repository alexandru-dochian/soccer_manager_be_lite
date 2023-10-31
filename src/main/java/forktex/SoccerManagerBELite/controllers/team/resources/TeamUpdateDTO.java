package forktex.SoccerManagerBELite.controllers.team.resources;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamUpdateDTO {
    @NotBlank(message = "name field cannot be blank!")
    String name;

    @NotBlank(message = "country field cannot be blank!")
    String country;
}
