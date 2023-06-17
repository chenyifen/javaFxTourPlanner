package at.fhtw.swen2.tutorial.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Builder
@Getter
@Setter
public class TourLog {
    private UUID logId;
    private String name;
    private String comment;
    private String difficulty;
    private String totalTime;
    private String rating;
    private Tour tour;

    public String toString(){
        StringBuilder builder = new StringBuilder(name);
        builder.append(comment);
        builder.append(difficulty);
        builder.append(totalTime);
        builder.append(rating);
        return builder().toString();
    }
}
