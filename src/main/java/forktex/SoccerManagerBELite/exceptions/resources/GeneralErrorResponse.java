package forktex.SoccerManagerBELite.exceptions.resources;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
public class GeneralErrorResponse {
    private HttpStatus httpStatus;
    private String errorMessage;
    private String stackTrace;
    private String details;
}