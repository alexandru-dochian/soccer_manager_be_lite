package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class PlayerNotActiveOnMarketException extends ApiException {
    public PlayerNotActiveOnMarketException(Long playerId) {
        super(ExceptionDefinition.PLAYER_NOT_ACTIVE_ON_MARKET_EXCEPTION, playerId);
    }
}
