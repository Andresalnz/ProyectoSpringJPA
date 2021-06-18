package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.Pilot;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PilotRepository extends CrudRepository<Pilot,Long> {
    public Optional<Pilot> findPilotByName(String name);
    public Optional<Pilot> findPilotByNameAndSurname(String name, String Surname);

}
