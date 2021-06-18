package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.controllers;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.Circuit;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.CircuitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
public class CircuitController {

    @Autowired
    private CircuitRepository circuitRepository;

    @GetMapping(value = "/circuit")
    public ResponseEntity<Object> circuitList() {
        return new ResponseEntity<>(circuitRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/circuit/detail/{id}")
    public ResponseEntity<?> pilotDetail(@PathVariable("id") Long id) {
        Circuit myCircuit = circuitRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id.toString()));
        return new ResponseEntity<>(new Object[]{myCircuit,myCircuit.getRace()},HttpStatus.OK);
    }

    @PostMapping(value="/circuit")
    public ResponseEntity<?> circuitAdd(@RequestParam("name") String name,
                                        @RequestParam("city") String city){
        Circuit newCircuit = new Circuit(name, city);
        Optional<Circuit> circuit = circuitRepository.findCircuitByNameAndCity(name,city);
        if(circuit.isPresent())return new ResponseEntity<>("Este piloto ya existe",HttpStatus.CONFLICT);
        circuitRepository.save(newCircuit);
        return new ResponseEntity<>(newCircuit, HttpStatus.OK);
    }

    @PostMapping(value = "/circuit/add")
    public ResponseEntity<?> circuitAdd(@RequestBody Circuit circuit) {
        Optional<Circuit> newCircuit = circuitRepository.findCircuitByNameAndCity(circuit.getName(), circuit.getCity());
        if(newCircuit.isPresent()) return new ResponseEntity<>("Ya existe",HttpStatus.CONFLICT);
        circuitRepository.save(circuit);
        return new ResponseEntity<>(circuit, HttpStatus.OK);
    }

    @DeleteMapping(value = "/circuit/{id}")
    public ResponseEntity<?> circuitDelete(@PathVariable("id") Long id) {
        circuitRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id.toString()));
        circuitRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/circuit/{id}")
    public ResponseEntity<?> circuitUpdate(@PathVariable("id") Long id, @RequestBody Circuit circuit) throws EntityNotFoundException{
        circuitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        circuitRepository.save(circuit);
        return new ResponseEntity<>(circuit, HttpStatus.OK);
    }

}
