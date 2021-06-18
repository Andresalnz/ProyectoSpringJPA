package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories;


import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team,Long> {
    public Optional<Team> findTeamByName(String name);

}
