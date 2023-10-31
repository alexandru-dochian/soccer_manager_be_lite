package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class TeamNotOwnedByTeamException extends ApiException {
    public TeamNotOwnedByTeamException(Long teamId, Long userId) {
        super(ExceptionDefinition.TEAM_NOT_OWNED_BY_USER_EXCEPTION, teamId, userId);
    }
}
