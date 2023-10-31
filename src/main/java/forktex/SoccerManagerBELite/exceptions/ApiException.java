package forktex.SoccerManagerBELite.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiException extends Exception {
    private HttpStatus httpStatus;

    public ApiException(String message) {
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApiException(ExceptionDefinition exceptionDefinition) {
        super(exceptionDefinition.getFormat());
        httpStatus = exceptionDefinition.getHttpStatus();
    }

    public ApiException(ExceptionDefinition exceptionDefinition, Object... args) {
        super(MessageFormatter.arrayFormat(exceptionDefinition.getFormat(), args).getMessage());
        httpStatus = exceptionDefinition.getHttpStatus();
    }
}