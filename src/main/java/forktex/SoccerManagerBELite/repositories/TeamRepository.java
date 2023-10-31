package forktex.SoccerManagerBELite.repositories;

import forktex.SoccerManagerBELite.repositories.entities.Team;
import forktex.SoccerManagerBELite.repositories.projections.PlayerProjection;
import forktex.SoccerManagerBELite.repositories.projections.TeamProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query(value = "select \n" +
            "p.id as playerId,\n" +
            "p.first_name as firstName,\n" +
            "p.last_name as lastName,\n" +
            "p.country as country,\n" +
            "p.type as \"type\",\n" +
            "p.market_value as marketValue,\n" +
            "p.\"age\" as \"age\"\n" +
            "from {h-schema}players p \n" +
            "inner join {h-schema}teams t on p.team_id = t.id\n" +
            "where t.id = :teamId \n", nativeQuery = true)
    List<PlayerProjection> getTeamPlayers(@Param("teamId") Long teamId);


    @Query(value = "select \n" +
            "\tt.\"name\" as \"name\",\n" +
            "\tt.country as country,\n" +
            "\tt.additional_value as additionalValue,\n" +
            "\t(\n" +
            "\t\tselect sum(p.market_value) from {h-schema}players p \n" +
            "\t\tinner join {h-schema}teams t on t.id = p.team_id \n" +
            "\t\twhere t.id = :teamId\n" +
            "\t) as value\t\n" +
            "from {h-schema}teams t \n" +
            "where t.id = :teamId", nativeQuery = true)
    TeamProjection getTeamInformation(@Param("teamId") Long teamId);

    @Query(value = "select count(*) > 0 from {h-schema}teams t\n" +
            "inner join {h-schema}users u on u.id = t.owned_by_user \n" +
            "where t.id = :teamId and u.id = :userId", nativeQuery = true)
    Boolean isTeamOwnedByUser(@Param("teamId") Long teamId, @Param("userId") Long userId);

    @Query(value = "select t.* from {h-schema}teams t \n" +
            "inner join {h-schema}players p on p.team_id = t.id\n" +
            "where p.id = :playerId", nativeQuery = true)
    Optional<Team> findTeamByPlayerId(@Param("playerId") Long playerId);

    Team findByOwnedByUser(Long userId);
}
