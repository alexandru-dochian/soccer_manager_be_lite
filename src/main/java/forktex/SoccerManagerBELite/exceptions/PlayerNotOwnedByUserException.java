package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class PlayerNotOwnedByUserException extends ApiException {
    public PlayerNotOwnedByUserException(Long playerId, Long userId) {
        super(ExceptionDefinition.PLAYER_NOT_OWNED_BY_USER_EXCEPTION, playerId, userId);
    }
}
