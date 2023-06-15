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


}