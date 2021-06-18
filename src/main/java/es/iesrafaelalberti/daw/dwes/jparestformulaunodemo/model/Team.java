package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String base;
    private String leader;
    private Integer yearCreation;
    private Integer championships;
    private String engine;
    private String imageURL;

    @JsonBackReference
    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
    Set<Pilot> pilots = new HashSet<>();

    public Team() {
    }

    public Team(String name, String base, String leader, Integer yearCreation, Integer championships, String engine) {
        this.name = name;
        this.base = base;
        this.leader = leader;
        this.yearCreation = yearCreation;
        this.championships = championships;
        this.engine = engine;
    }
}
