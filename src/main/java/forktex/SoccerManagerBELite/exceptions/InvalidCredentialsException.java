package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class InvalidCredentialsException extends ApiException {
    public InvalidCredentialsException() {
        super(ExceptionDefinition.INVALID_CREDENTIALS_EXCEPTION);
    }
}
