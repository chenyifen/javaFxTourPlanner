package at.fhtw.swen2.tutorial.persistence.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.type.UUIDCharType;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Entity
@Slf4j
/*
name, tour description, from, to, transport type, tour distance,
estimated time, route information (an image with the tour map)

Tour Log:  date/time, comment, difficulty, total time, and rating
 */
@Table(name = "tour_log")
@TypeDefs({@TypeDef(name = "uuid-binary", typeClass = UUIDCharType.class)})
public class TourLogEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Type(type = "uuid-binary") // fix sql error
    @Column(name = "id",insertable = false, nullable = false, updatable = false)
    private UUID logId;

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

    @Column (name = "tour_id")
    Long tourId;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", referencedColumnName = "tour_id", insertable = false, updatable = false)
    private TourEntity tour;

    public TourLogEntity() {
        this.logId = UUID.randomUUID();
    }

    public void setTour(TourEntity tourEntity){
        this.tour = tourEntity;
        log.info("TourLogEntity set tourId by tour : " + tourEntity.getTour_id());
        this.tourId = tourEntity.getTour_id();
    }



}
