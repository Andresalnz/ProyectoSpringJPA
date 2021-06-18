package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.Race;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RaceRepository extends CrudRepository<Race,Long> {

    @Query("SELECT pr.pilot.name FROM PilotRace pr GROUP BY (pr.point),(pr.pilot.name)")
    List<String> findAllRace(Sort sort);

    @Query("SELECT sum(pr.point) FROM PilotRace pr where pr.pilot.id=:id ")
    Integer findByPilotRacesPoints(@Param("id")Long id);

    @Query("SELECT sum(pr.point) FROM PilotRace pr JOIN Pilot p ON p.id=pr.pilot.id JOIN City c ON c.id =:id")
    Integer findByCityPoints(@Param("id")Long id);
}
