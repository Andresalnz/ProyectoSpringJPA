package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.controllers;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.City;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping(value = "/city")
    public ResponseEntity<Object> cityList() {
        return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/city/detail/{id}")
    public ResponseEntity<?> detailCity(@PathVariable("id") Long id) {
        City myCity = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        return new ResponseEntity<>(new Object[]{myCity,myCity.getPilots()}, HttpStatus.OK);
    }

    @PostMapping(value = "/city") ResponseEntity<?> cityAdd(@RequestParam("name")String name) {
        City newCity = new City(name);
        Optional<City> city = cityRepository.findCityByName(name);
        if(city.isPresent())return new ResponseEntity<>("Esa ciudad ya existe",HttpStatus.CONFLICT);
        cityRepository.save(newCity);
        return new ResponseEntity<>(newCity, HttpStatus.OK);
    }

    @PostMapping(value = "/city/add")
    public ResponseEntity<?> cityAdd(@RequestBody City city) {
        Optional<City> newCity = cityRepository.findCityByName(city.getName());
        if(newCity.isPresent()) return new ResponseEntity<>("Ya existe",HttpStatus.CONFLICT);
        cityRepository.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @DeleteMapping(value = "/city/{id}")
    public ResponseEntity<?> cityDelete(@PathVariable("id") Long id) {
        cityRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id.toString()));
        cityRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/city/{id}")
    public ResponseEntity<?> cityUpdate(@PathVariable("id") Long id, @RequestBody City city) throws EntityNotFoundException{
        cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        cityRepository.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }
}
