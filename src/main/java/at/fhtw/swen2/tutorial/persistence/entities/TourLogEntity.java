package at.fhtw.swen2.tutorial.persistence.entities;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
@Table(name = "tour_log")

public class TourLogEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String comment;
    @Column
    private String difficulty;
    @Column(name = "total_time")
    private String totalTime;
    @Column
    private String rating;

    @Column(name = "tour_id", insertable = false, updatable = false)
    private Long tourId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private TourEntity tour;

}
