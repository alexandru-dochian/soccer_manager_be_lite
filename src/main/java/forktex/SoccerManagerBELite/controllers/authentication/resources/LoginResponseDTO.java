package forktex.SoccerManagerBELite.controllers.authentication.resources;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginResponseDTO {
    private String firstName;
    private String lastName;
    private String token;
}
