package at.fhtw.swen2.tutorial.service.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MapQuestRoute {

    private double distance;

    private String mapUrl;

    private int time;

}
