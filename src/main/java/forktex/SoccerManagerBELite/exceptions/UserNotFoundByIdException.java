package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class UserNotFoundByIdException extends ApiException {
    public UserNotFoundByIdException(Long userId) {
        super(ExceptionDefinition.USER_NOT_FOUND_BY_ID_EXCEPTION, userId);
    }
}
