package at.fhtw.swen2.tutorial.service.dto;

import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@Getter
@Setter
public class Tour {
    private Long tourId;
    private String name;
    private String description;
    private String from;
    private String to;
    private String transportType;
    private String tourDistance;
    private String estimatedTime;
    private String routeInformation;
    private List<TourLogEntity> tourLogs;


    public String toString(){
        StringBuilder builder = new StringBuilder(name);
        builder.append(description);
        builder.append(from);
        builder.append(to);
        builder.append(transportType);
        builder.append(tourDistance);
        builder.append(estimatedTime);
        builder.append(routeInformation);
        return builder.toString();
    }

}
