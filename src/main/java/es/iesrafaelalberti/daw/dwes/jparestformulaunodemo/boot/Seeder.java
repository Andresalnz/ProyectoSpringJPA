package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.boot;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.*;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class Seeder implements CommandLineRunner {
    @Autowired
    private PilotRepository pilotRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private CircuitRepository circuitRepository;
    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PilotRaceRepository pilotRacerepository;


    @Override
    public void run(String[] args) throws Exception {

        Role role1 = roleRepository.save(new Role("ROLE_ADMIN"));
        Role role2 = roleRepository.save(new Role("ROLE_BOSS"));
        Role role3 = roleRepository.save(new Role("ROLE_GOD"));
        User user1 = userRepository.save(new User("Andres", "java", role1));
        User user2 = userRepository.save(new User("Pepe", "js", role2));
        User user3 = userRepository.save(new User("Vero", "react", role3));

        Circuit c1 = circuitRepository.save(new Circuit("Montmelo","Barcelona"));
        Circuit c2 = circuitRepository.save(new Circuit("Montecarlo","Monaco"));
        Circuit c3 = circuitRepository.save(new Circuit("Portimao","Portugal"));
        Circuit c4 = circuitRepository.save(new Circuit("Baku","Azerbaiyán"));
        Circuit c5 = circuitRepository.save(new Circuit("Bahrein","Sakhir"));

        City ciu1 = cityRepository.save(new City("Cadiz"));
        City ciu2 = cityRepository.save(new City("Sevilla"));
        City ciu3 = cityRepository.save(new City("Malaga"));

        Team t1 = teamRepository.save(new Team("Redbull","United Kingdom","Christian Horner",1997,4,"Honda"));
        Team t2 = teamRepository.save(new Team("Alpine","United Kingdom","Davide Brivio",1986,2,"Renault"));
        Team t3 = teamRepository.save(new Team("Ferrari","Italy","Mattia Binotto",1950,16,"Ferrari"));
        Team t4 = teamRepository.save(new Team("Mercedes"," United Kingdom","Toto Wolff",1970,5,"Mercedes"));

        Pilot p1 = pilotRepository.save(new Pilot("Carlos","Sainz",31,"Spain",1,97,t3,ciu1));
        Pilot p2 = pilotRepository.save(new Pilot("Fernando","Alonso",31,"Spain",2,97,t2,ciu1));
        Pilot p3 = pilotRepository.save(new Pilot("Esteban","Ocon",22,"France",0,2,t2,ciu2));
        Pilot p4 = pilotRepository.save(new Pilot("Max","Verstappen",24,"Netherlands",0,2,t1,ciu2));
        Pilot p5 = pilotRepository.save(new Pilot("Sergio","Perez",35,"Mexico",0,11,t1,ciu3));
        Pilot p6 = pilotRepository.save(new Pilot("Valtteri","Bottas",31,"Finland",0,59,t4,ciu3));


        Race r1 = raceRepository.save(new Race(c1));
        Race r2 = raceRepository.save(new Race(c2));
        Race r3 = raceRepository.save(new Race(c3));
        Race r4 = raceRepository.save(new Race(c4));
        Race r5 = raceRepository.save(new Race(c5));

        PilotRace pr1 = pilotRacerepository.save(new PilotRace(p1,r1,25,1));
        PilotRace pr2 = pilotRacerepository.save(new PilotRace(p4,r1,20,2));
        PilotRace pr3 = pilotRacerepository.save(new PilotRace(p2,r1,10,3));
        PilotRace pr4 = pilotRacerepository.save(new PilotRace(p6,r2,1,9));
        PilotRace pr5 = pilotRacerepository.save(new PilotRace(p5,r2,5,4));
        PilotRace pr6 = pilotRacerepository.save(new PilotRace(p1,r2,5,4));

    }
}
