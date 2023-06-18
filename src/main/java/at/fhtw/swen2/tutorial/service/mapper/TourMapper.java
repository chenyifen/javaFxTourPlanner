package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class  TourMapper extends AbstractMapper<TourEntity, Tour> {

    @Override
    public Tour fromEntity(TourEntity entity) {
        if(entity == null){
            return Tour.builder().build();
        }
        return Tour.builder()
                .tourId(entity.getTour_id())
                .name(entity.getName())
                .from(entity.getFromLocation())
                .to(entity.getToLocation())
                .description(entity.getDescription())
                .estimatedTime(entity.getEstimatedTime())
                .tourDistance(entity.getTourDistance())
                .routeInformation(entity.getRouteInformation())
                .transportType(entity.getTransportType())
                .tourLogs(entity.getTourLogs())
                .build();
    }

    @Override
    public TourEntity toEntity(Tour tour) {
        return TourEntity.builder()
                .tour_id(tour.getTourId())
                .name(tour.getName())
                .description(tour.getDescription())
                .estimatedTime(tour.getEstimatedTime())
                .fromLocation(tour.getFrom())
                .toLocation(tour.getTo())
                .routeInformation(tour.getRouteInformation())
                .tourDistance(tour.getTourDistance())
                .transportType(tour.getTransportType())
                .tourLogs(tour.getTourLogs())
                .build();
    }

}
