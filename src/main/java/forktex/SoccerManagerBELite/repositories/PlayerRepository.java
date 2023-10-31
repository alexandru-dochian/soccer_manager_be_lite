package forktex.SoccerManagerBELite.repositories;

import forktex.SoccerManagerBELite.repositories.entities.Player;
import forktex.SoccerManagerBELite.repositories.projections.PlayerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query(value = "select \n" +
            "p.id as playerId,\n" +
            "p.first_name as firstName,\n" +
            "p.last_name as lastName,\n" +
            "p.country as country,\n" +
            "p.type as \"type\",\n" +
            "p.market_value as marketValue,\n" +
            "p.\"age\" as \"age\"\n" +
            "from {h-schema}players p \n" +
            "where p.id = :playerId \n", nativeQuery = true)
    PlayerProjection getPlayerInformation(@Param("playerId") Long playerId);

    @Query(value = "select count(*) > 0 from players p \n" +
            "inner join teams t on p.team_id = t.id\n" +
            "inner join users u on u.id = t.owned_by_user \n" +
            "where p.id = :playerId and u.id = :userId", nativeQuery = true)
    Boolean isPlayerOwnedByUser(@Param("playerId") Long playerId, @Param("userId") Long userId);
}
