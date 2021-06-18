package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.controllers;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.Pilot;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.PilotRepository;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
public class PilotController {

    @Autowired
    private PilotRepository pilotRepository;
    @Autowired
    private RaceRepository raceRepository;

    @GetMapping(value = "/pilot")
    public ResponseEntity<Object> pilotList() {

        return new ResponseEntity<>(pilotRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/pilot/detail/{id}")
    public ResponseEntity<?> pilotDetail(@PathVariable("id") Long id) {
       Pilot myPilot = pilotRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id.toString()));
        return new ResponseEntity<>(new Object[]{pilotRepository.findById(id),myPilot.getPilotRaces()},HttpStatus.OK);
    }

    @PostMapping(value = "/pilot")
    public ResponseEntity<?> pilotAdd(@RequestParam("name") String name,
                                      @RequestParam("surname") String surname,
                                      @RequestParam("age") Integer age,
                                      @RequestParam("country") String country,
                                      @RequestParam("championships") Integer championships,
                                      @RequestParam("podiums") Integer podiums) {
        Pilot newPilot = new Pilot(name, surname, age,country,championships,podiums);
        Optional<Pilot> pilot = pilotRepository.findPilotByNameAndSurname(name,surname);
        if(pilot.isPresent())return new ResponseEntity<>("Este piloto ya existe",HttpStatus.CONFLICT);
        pilotRepository.save(newPilot);
        return new ResponseEntity<>(newPilot, HttpStatus.OK);
    }

    @PostMapping(value = "/pilot/add")
    public ResponseEntity<?> pilotAdd(@RequestBody Pilot pilot) {
        Optional<Pilot> newPilot = pilotRepository.findPilotByNameAndSurname(pilot.getName(), pilot.getSurname());
        if(newPilot.isPresent()) return new ResponseEntity<>("Ya existe",HttpStatus.CONFLICT);
        pilotRepository.save(pilot);
        return new ResponseEntity<>(pilot, HttpStatus.OK);
    }

    @DeleteMapping(value = "/pilot/{id}")
    public ResponseEntity<?> pilotDelete(@PathVariable("id") Long id) {
        pilotRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id.toString()));
        pilotRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/pilot/{id}")
    public ResponseEntity<?> pilotUpdate(@PathVariable("id") Long id, @RequestBody Pilot pilot) throws EntityNotFoundException{
        pilotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        pilotRepository.save(pilot);
        return new ResponseEntity<>(pilot, HttpStatus.OK);
    }
}