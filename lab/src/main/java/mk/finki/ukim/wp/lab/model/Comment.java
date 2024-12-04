package mk.finki.ukim.wp.lab.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String comment;
    @ManyToOne
    User user;
    @ManyToOne
    Event event;

    public Comment(String comment, User user) {
        this.comment = comment;
        this.user = user;
    }
}

