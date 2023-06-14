package at.fhtw.swen2.tutorial.persistence.entities;

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
//@Table(name = "tours")

public class TourEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        private Long id;
        @Column
        private String name;
        @Column
        private String description;
        @Column
        private String fromLocation;
        @Column
        private String toLocation;
        @Column
        private String transportType;
        @Column
        private String tourDistance;
        @Column
        private String estimatedTime;
        @Column
        private String routeInformation;


}
