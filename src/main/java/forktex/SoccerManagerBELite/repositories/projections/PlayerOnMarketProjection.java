package forktex.SoccerManagerBELite.repositories.projections;

public interface PlayerOnMarketProjection extends PlayerProjection {
    Long getMarketId();
    Double getRequestedPrice();
    Long getTeamId();
}
