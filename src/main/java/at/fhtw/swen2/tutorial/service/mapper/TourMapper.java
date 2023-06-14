package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.springframework.stereotype.Component;

@Component
public class TourMapper extends AbstractMapper<TourEntity, Tour> {

    @Override
    public Tour fromEntity(TourEntity entity) {
        return Tour.builder()
                .id(entity.getId())
                .name(entity.getName())
                .from(entity.getFromLocation())
                .to(entity.getToLocation())
                .description(entity.getDescription())
                .estimatedTime(entity.getEstimatedTime())
                .tourDistance(entity.getTourDistance())
                .routeInformation(entity.getRouteInformation())
                .transportType(entity.getTransportType())
                .build();
    }

    @Override
    public TourEntity toEntity(Tour tour) {
        return TourEntity.builder()
                .id(tour.getId())
                .name(tour.getName())
                .description(tour.getDescription())
                .estimatedTime(tour.getEstimatedTime())
                .fromLocation(tour.getFrom())
                .toLocation(tour.getTo())
                .routeInformation(tour.getRouteInformation())
                .tourDistance(tour.getTourDistance())
                .transportType(tour.getTransportType())
                .build();
    }

}
