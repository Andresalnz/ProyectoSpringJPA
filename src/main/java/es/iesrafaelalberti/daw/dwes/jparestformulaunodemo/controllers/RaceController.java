package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.controllers;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.Race;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
public class RaceController {

    @Autowired
    private RaceRepository raceRepository;

    @GetMapping(value = "/race")
    public ResponseEntity<Object> raceList() {
        return new ResponseEntity<>(raceRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/race/detail/{id}")
    public ResponseEntity<?> raceDetail(@PathVariable("id") Long id) {
        Race myrace = raceRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id.toString()));
        return new ResponseEntity<>(raceRepository.findById(id),HttpStatus.OK);
    }

    @PostMapping(value = "/race/add")
    public ResponseEntity<?> raceAdd(@RequestBody Race race) {
        Optional<Race> newRace = raceRepository.findById(race.getId());
        if(newRace.isPresent()) return new ResponseEntity<>("Ya existe",HttpStatus.CONFLICT);
        raceRepository.save(race);
        return new ResponseEntity<>(race, HttpStatus.OK);
    }

    @DeleteMapping(value = "/race/{id}")
    public ResponseEntity<?> raceDelete(@PathVariable("id") Long id) {
        raceRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id.toString()));
        raceRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/race/{id}")
    public ResponseEntity<?> raceUpdate(@PathVariable("id") Long id, @RequestBody Race race) throws EntityNotFoundException{
        raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        raceRepository.save(race);
        return new ResponseEntity<>(race, HttpStatus.OK);
    }


}
