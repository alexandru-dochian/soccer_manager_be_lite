package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class UserNotFoundByEmailException extends ApiException {
    public UserNotFoundByEmailException(String email) {
        super(ExceptionDefinition.USER_NOT_FOUND_BY_EMAIL_EXCEPTION, email);
    }
}
