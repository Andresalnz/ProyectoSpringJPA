package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PilotRace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private Integer point;
    private Integer position;

    @ManyToOne
    @JoinColumn()
    Pilot pilot;

    @ManyToOne
    @JoinColumn()
    Race race;

    public PilotRace() {
    }

    public PilotRace(Pilot pilot, Race race,Integer point,Integer position) {
        this.pilot = pilot;
        this.race = race;
        this.point=point;
        this.position=position;
    }

    public PilotRace(Integer point) {
        this.point = point;
    }
}
