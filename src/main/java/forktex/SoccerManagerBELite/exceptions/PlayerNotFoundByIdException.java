package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class PlayerNotFoundByIdException extends ApiException {
    public PlayerNotFoundByIdException(Long playerId) {
        super(ExceptionDefinition.PLAYER_NOT_FOUND_BY_ID_EXCEPTION, playerId);
    }
}
