package forktex.SoccerManagerBELite.repositories.projections;

public interface PlayerProjection {
    Long getPlayerId();
    String getFirstName();
    String getLastName();
    String getCountry();
    String getType();
    Double getMarketValue();
    Integer getAge();
}
