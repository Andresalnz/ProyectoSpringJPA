package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL)
    Set<Pilot> pilots = new HashSet<>();

    public City() {
    }

    public City(String name) {
        this.name = name;
    }
}
