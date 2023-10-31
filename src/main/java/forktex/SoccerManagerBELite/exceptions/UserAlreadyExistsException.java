package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException(String email) {
        super(ExceptionDefinition.USER_ALREADY_EXISTS_EXCEPTION, email);
    }
}
