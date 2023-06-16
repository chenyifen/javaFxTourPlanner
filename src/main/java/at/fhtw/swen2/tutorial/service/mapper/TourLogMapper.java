package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.stereotype.Component;

@Component
public class TourLogMapper extends AbstractMapper<TourLogEntity, TourLog> {

    @Override
    public TourLog fromEntity(TourLogEntity entity) {
        return TourLog.builder()
                .id(entity.getId())
                .name(entity.getName())
                .comment(entity.getComment())
                .difficulty(entity.getDifficulty())
                .totalTime(entity.getTotalTime())
                .rating(entity.getRating())
                .tourId(entity.getTourId())
                .build();
    }

    @Override
    public TourLogEntity toEntity(TourLog tourLog) {
        return TourLogEntity.builder()
                .id(tourLog.getId())
                .name(tourLog.getName())
                .comment(tourLog.getComment())
                .difficulty(tourLog.getDifficulty())
                .totalTime(tourLog.getTotalTime())
                .rating(tourLog.getRating())
                .tourId(tourLog.getTourId())
                .build();
    }

}
