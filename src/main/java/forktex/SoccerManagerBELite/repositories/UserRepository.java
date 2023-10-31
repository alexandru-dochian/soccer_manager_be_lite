package forktex.SoccerManagerBELite.repositories;

import forktex.SoccerManagerBELite.repositories.entities.User;
import forktex.SoccerManagerBELite.repositories.projections.ProfileProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value = "select \n" +
            "\tu.first_name as firstName,\n" +
            "\tu.last_name as lastName,\n" +
            "\tt.id as teamId\n" +
            "from {h-schema}users u\n" +
            "inner join {h-schema}teams t on t.owned_by_user = u.id\n" +
            "where u.id = :userId", nativeQuery = true)
    ProfileProjection getProfile(@Param("userId") Long userId);
}
