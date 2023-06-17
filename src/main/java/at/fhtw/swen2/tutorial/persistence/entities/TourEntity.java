package at.fhtw.swen2.tutorial.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "tour")

public class TourEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "tour_id")
        private Long tour_id;
        @Column(name = "name")
        private String name;
        @Column(name = "description")
        private String description;
        @Column(name = "from_location")
        private String fromLocation;
        @Column(name = "to_location")
        private String toLocation;
        @Column(name = "transport_type")
        private String transportType;
        @Column(name = "tour_distance")
        private String tourDistance;
        @Column(name = "estimated_time")
        private String estimatedTime;
        @Column(name = "route_information")
        private String routeInformation;
        @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<TourLogEntity> tourLogs = new ArrayList<>();
}
