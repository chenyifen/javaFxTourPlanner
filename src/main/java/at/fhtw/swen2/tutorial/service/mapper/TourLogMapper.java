package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.stereotype.Component;

@Component
public class TourLogMapper extends AbstractMapper<TourLogEntity, TourLog> {

    @Override
    public TourLog fromEntity(TourLogEntity entity) {
        TourLog.TourLogBuilder builder = TourLog.builder()
                .logId(entity.getLogId())
                .name(entity.getName())
                .comment(entity.getComment())
                .difficulty(entity.getDifficulty())
                .totalTime(entity.getTotalTime())
                .rating(entity.getRating());
        if(entity.getTour() != null){
            builder.tour(new TourMapper().fromEntity(entity.getTour()));
        }
        return builder.build();
    }

    @Override
    public TourLogEntity toEntity(TourLog tourLog) {
        return TourLogEntity.builder()
                .logId(tourLog.getLogId())
                .name(tourLog.getName())
                .comment(tourLog.getComment())
                .difficulty(tourLog.getDifficulty())
                .totalTime(tourLog.getTotalTime())
                .rating(tourLog.getRating())
                .tour(new TourMapper().toEntity(tourLog.getTour()))
                .build();
    }

}
