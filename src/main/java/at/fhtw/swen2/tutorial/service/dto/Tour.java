package at.fhtw.swen2.tutorial.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tour {
    private Long id;
    private String name;
    private String description;
    private String from;
    private String to;
    private String transportType;
    private String tourDistance;
    private String estimatedTime;
    private String routeInformation;

    public String toString(){
        StringBuilder builder = new StringBuilder(name);
        builder.append(description);
        builder.append(from);
        builder.append(to);
        builder.append(transportType);
        builder.append(tourDistance);
        builder.append(estimatedTime);
        builder.append(routeInformation);
        return builder().toString();
    }

}
