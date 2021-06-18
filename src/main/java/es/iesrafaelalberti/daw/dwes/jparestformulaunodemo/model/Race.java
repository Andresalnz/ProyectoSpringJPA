package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter @Setter
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne()
    @JoinColumn()
    private Circuit circuit;

    @JsonBackReference
    @OneToMany(mappedBy = "race",cascade = CascadeType.ALL)
    Set<PilotRace> pilotRaces = new HashSet<>();

    public Race() {
    }

    public Race(Circuit circuit) {
        this.circuit = circuit;
    }
}
