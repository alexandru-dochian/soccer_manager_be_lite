package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class TeamNotFoundByIdException extends ApiException {
    public TeamNotFoundByIdException(Long teamId) {
        super(ExceptionDefinition.TEAM_NOT_FOUND_BY_ID_EXCEPTION, teamId);
    }
}
