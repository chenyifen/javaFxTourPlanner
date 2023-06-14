package at.fhtw.swen2.tutorial.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
/*
name, tour description, from, to, transport type, tour distance,
estimated time, route information (an image with the tour map)

Tour Log:  date/time, comment, difficulty, total time, and rating
 */
public class TourLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String name;
    private String comment;
    private String difficulty;
    private String totalTime;
    private String rating;


}
