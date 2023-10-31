package forktex.SoccerManagerBELite.repositories;

import forktex.SoccerManagerBELite.repositories.entities.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<Log, Long> {
}

