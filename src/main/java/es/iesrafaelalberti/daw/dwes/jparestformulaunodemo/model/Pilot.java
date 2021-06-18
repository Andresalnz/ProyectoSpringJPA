package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter @Setter
public class Pilot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    private Integer age;
    private String country;
    private Integer championships;
    private Integer podiums;
    private String imageURL;

    @ManyToOne()
    @JoinColumn()
    private Team team;

    @ManyToOne()
    @JoinColumn()
    private City city;

    @JsonBackReference
    @OneToMany(mappedBy = "pilot",cascade = CascadeType.ALL)
    Set<PilotRace> pilotRaces = new HashSet<>();


    public Pilot (){
    }

    public Pilot(String name, String surname, Integer age, String country, Integer championships, Integer podiums) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.country = country;
        this.championships = championships;
        this.podiums = podiums;
    }

    public Pilot(String name, String surname, Integer age, String country, Integer championships, Integer podiums, Team team,City city) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.country = country;
        this.championships = championships;
        this.podiums = podiums;
        this.team = team;
        this.city=city;
    }
}
