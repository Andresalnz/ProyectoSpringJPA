package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.City;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CityRepository extends CrudRepository<City,Long> {
    public Optional<City> findCityByName(String name);

}
