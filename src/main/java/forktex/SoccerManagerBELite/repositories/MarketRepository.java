package forktex.SoccerManagerBELite.repositories;

import forktex.SoccerManagerBELite.repositories.entities.Market;
import forktex.SoccerManagerBELite.repositories.projections.PlayerOnMarketProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
    @Query(value = "select\n" +
            "\tm.id as marketId,\n" +
            "\tm.requested_price as requestedPrice,\n" +
            "\tp.team_id as teamId,\n" +
            "p.id as playerId,\n" +
            "p.first_name as firstName,\n" +
            "p.last_name as lastName,\n" +
            "p.country as country,\n" +
            "p.type as \"type\",\n" +
            "p.market_value as marketValue,\n" +
            "p.\"age\" as \"age\"\n" +
            "from {h-schema}market m\n" +
            "inner join {h-schema}players p on p.id = m.player_id \n" +
            "where m.finalized = false and m.canceled = false", nativeQuery = true)
    List<PlayerOnMarketProjection> getPlayersOnMarket();

    @Query(value = "select count(*) > 0 from {h-schema}market m \n" +
            "inner join {h-schema}players p on p.id = m.player_id \n" +
            "where p.id = :playerId and m.finalized = false and m.canceled = false", nativeQuery = true)
    Boolean isPlayerAlreadyActiveOnMarket(@Param("playerId") Long playerId);

    Market findByPlayerIdAndFinalizedFalseAndCanceledFalse(Long playerId);
}
