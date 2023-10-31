package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class PlayerAlreadyActiveOnMarketException extends ApiException {
    public PlayerAlreadyActiveOnMarketException(Long playerId) {
        super(ExceptionDefinition.PLAYER_ALREADY_ACTIVE_ON_MARKET_EXCEPTION, playerId);
    }
}
