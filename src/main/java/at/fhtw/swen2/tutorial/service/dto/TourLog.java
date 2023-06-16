package at.fhtw.swen2.tutorial.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourLog {
    private Long id;
    private String name;
    private String comment;
    private String difficulty;
    private String totalTime;
    private String rating;
    private Long tourId;

    public String toString(){
        StringBuilder builder = new StringBuilder(name);
        builder.append(comment);
        builder.append(difficulty);
        builder.append(totalTime);
        builder.append(rating);
        return builder().toString();
    }
}
