package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.controllers;


import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.PilotRepository;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClasificationController {

    @Autowired
    private PilotRepository pilotRepository;

    @Autowired
    private RaceRepository raceRepository;

    @GetMapping(value = "/clasification")
    public ResponseEntity<?> clasification (){
        return new ResponseEntity<>(new Object[]{raceRepository.findAllRace(Sort.by(Sort.Direction.DESC, "point"))},HttpStatus.OK);
    }

    @GetMapping(value = "/clasification/pilot/{id}")
    public ResponseEntity<?> clasificationPoints (@PathVariable("id")Long id){
        return new ResponseEntity<>(raceRepository.findByPilotRacesPoints(id)+" Points",HttpStatus.OK);
    }

    @GetMapping(value = "/clasification/city/{id}")
    public ResponseEntity<?> clasificationCityPoints (@PathVariable("id")Long id){
        return new ResponseEntity<>(raceRepository.findByCityPoints(id)+" Points",HttpStatus.OK);
    }

}
