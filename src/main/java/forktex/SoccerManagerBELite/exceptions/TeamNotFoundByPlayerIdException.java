package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class TeamNotFoundByPlayerIdException extends ApiException {
    public TeamNotFoundByPlayerIdException(Long playerId) {
        super(ExceptionDefinition.TEAM_NOT_FOUND_BY_PLAYER_ID_EXCEPTION, playerId);
    }
}
