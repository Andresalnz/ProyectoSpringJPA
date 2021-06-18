package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.controllers;


import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.Team;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping(value = "/team")
    public ResponseEntity<?> listTeam() {
        return new ResponseEntity<>(teamRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/team/detail/{id}")
    public ResponseEntity<?> detailTeam(@PathVariable("id") Long id) {
        Team myteam = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        return new ResponseEntity<>(new Object[]{myteam,myteam.getPilots()}, HttpStatus.OK);
    }

    @PostMapping(value = "/team/add")
    public ResponseEntity<?> teamAdd(@RequestBody Team team) {
        Optional<Team> newTeam = teamRepository.findTeamByName(team.getName());
        if (newTeam.isPresent()) return new ResponseEntity<>("Ese equipo ya esta", HttpStatus.CONFLICT);
        teamRepository.save(team);
        return new ResponseEntity<>("Equipo añadido", HttpStatus.OK);
    }

    @DeleteMapping(value = "/team/{id}")
    public ResponseEntity<?> teamDelete(@PathVariable("id") Long id) {
        teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        teamRepository.deleteById(id);
        return new ResponseEntity<>("Equipo borrado", HttpStatus.OK);
    }

    @PutMapping(value = "/team/{id}")
    public ResponseEntity<?> teamUpdate(@PathVariable("id") Long id, @RequestBody Team team) {
        teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        teamRepository.save(team);
        return new ResponseEntity<>("Team actualizado", HttpStatus.OK);
    }
}
