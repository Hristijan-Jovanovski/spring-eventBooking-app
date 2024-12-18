package mk.finki.ukim.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
public class Event {
    private String name;
    private String description;
    private double popularityScore;
    private int available;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Location location;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Comment> comments;


    public Event(String name, String description, double popularityScore, int available, Location location,List<Comment>comments) {
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.available = available;
        this.location=location;
        this.comments=comments;

    }
    public Event(String name, String description, double popularityScore, int available, Location location) {
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.available = 10;
        this.location=location;

    }
    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", popularityScore=" + popularityScore +
                ", available=" + available +
                ", location=" + (location != null ? location.getName() : "No location") +
                '}';
    }
}



