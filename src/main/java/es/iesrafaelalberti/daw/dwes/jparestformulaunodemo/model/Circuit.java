package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Circuit {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String city;

    @JsonBackReference
    @OneToOne(mappedBy = "circuit")
    private Race race;


    public Circuit() {
    }

    public Circuit(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Circuit(String name, String city, Race race) {
        this.name = name;
        this.city = city;
        this.race = race;
    }
}
