package mk.finki.ukim.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.wp.lab.config.WebSecurityConfig;
import mk.finki.ukim.wp.lab.model.*;
import mk.finki.ukim.wp.lab.model.enumerations.Role;
import mk.finki.ukim.wp.lab.repository.jpa.EventRepository;
import mk.finki.ukim.wp.lab.repository.jpa.LocationRepository;
import mk.finki.ukim.wp.lab.repository.jpa.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Event> events = null;
    public static List<EventBooking> bookingList = null;
    public static List<Location> locationList = null;
    public static List<User> users = null;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;



    public DataHolder(PasswordEncoder passwordEncoder, UserRepository userRepository, EventRepository eventRepository, LocationRepository locationRepository, WebSecurityConfig webSecurityConfig) {
        this.passwordEncoder = passwordEncoder;

        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;

    }


    @PostConstruct
    public void init() {
        events = new ArrayList<>();
        events.add(new Event("brucoska", "party", 2.0, 5, new Location(1L, "Skopje", "232", "232", "323")));
        events.add(new Event("Event1", "dis1", 4.2, 2, new Location(2L, "Kumanovo", "232", "232", "323")));
        events.add(new Event("Event2", "dis2", 4.2, 6, new Location(3L, "Bitola", "232", "232", "323")));
        events.add(new Event("Event3", "dis3", 4.4, 5, new Location(4L, "Ohrid", "232", "232", "323")));
        events.add(new Event("Oppenheimer", "movie", 3.2, 9, new Location(5L, "Tetovo", "232", "232", "323")));
        events.add(new Event("Barbie", "movie", 5.0, 8, new Location(1L, "Skopje", "232", "232", "323")));
        events.add(new Event("Titanic", "movie", 2.4, 7, new Location(2L, "Kumanovo", "232", "232", "323")));
        events.add(new Event("Christmas-party", "party", 2.0, 6, new Location(3L, "Bitola", "232", "232", "323")));
        events.add(new Event("game_night", "entertainment", 4.2, 7, new Location(4L, "Ohrid", "232", "232", "323")));
        events.add(new Event("stnad_up_show0", "entertainment", 4.2, 3, new Location(5L, "Tetovo", "232", "232", "323")));

        bookingList = new ArrayList<>();
        locationList = new ArrayList<>();
        locationList.add(new Location(1L, "Skopje", "232", "232", "323"));
        locationList.add(new Location(2L, "Kumanovo", "232", "232", "323"));
        locationList.add(new Location(3L, "Bitola", "232", "232", "323"));
        locationList.add(new Location(4L, "Ohird", "232", "232", "323"));
        locationList.add(new Location(5L, "Tetovo", "232", "232", "323"));
this.locationRepository.saveAll(locationList);


        users = new ArrayList<>();
        users.add(new User("hristijan", passwordEncoder.encode("hh"), "Hristijan", "Jovanovski",Role.ROLE_USER));
        users.add(new User("elena.atanasoska", passwordEncoder.encode("ea"), "Elena", "Atanasoska", Role.ROLE_USER));
        users.add(new User("darko.sasanski", passwordEncoder.encode("ds"), "Darko", "Sasanski", Role.ROLE_USER));
        users.add(new User("ana.todorovska", passwordEncoder.encode("at"), "Ana", "Todorovska", Role.ROLE_USER));
        users.add(new User("admin", passwordEncoder.encode("admin"), "admin", "admin", Role.ROLE_ADMIN));
      userRepository.saveAll(users);
    }
}
