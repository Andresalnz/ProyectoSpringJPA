package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.Circuit;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CircuitRepository extends CrudRepository<Circuit,Long> {
    public Optional<Circuit> findCircuitByNameAndCity(String name, String city);

}
