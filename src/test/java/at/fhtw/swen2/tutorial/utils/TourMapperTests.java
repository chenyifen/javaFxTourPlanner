package at.fhtw.swen2.tutorial.utils;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.mapper.TourMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TourMapperTests {

    private TourMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new TourMapper();
    }

    @Test
    public void testFromEntity() {
        TourEntity entity = TourEntity.builder()
                .tour_id(1L)
                .name("name")
                .fromLocation("from")
                .toLocation("to")
                .description("description")
                .estimatedTime("estimatedTime")
                .routeInformation("routeInformation")
                .transportType("transportType")
                .tourDistance("tourDistance")
                .build();

        Tour result = mapper.fromEntity(entity);

        assertEquals(entity.getTour_id(), result.getTourId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getFromLocation(), result.getFrom());
        assertEquals(entity.getToLocation(), result.getTo());
        assertEquals(entity.getDescription(), result.getDescription());
        assertEquals(entity.getEstimatedTime(), result.getEstimatedTime());
        assertEquals(entity.getRouteInformation(), result.getRouteInformation());
        assertEquals(entity.getTransportType(), result.getTransportType());
        assertEquals(entity.getTourDistance(), result.getTourDistance());
    }

    @Test
    public void testToEntity() {
        Tour tour = Tour.builder()
                .tourId(1L)
                .name("name")
                .from("from")
                .to("to")
                .description("description")
                .estimatedTime("estimatedTime")
                .routeInformation("routeInformation")
                .transportType("transportType")
                .tourDistance("tourDistance")
                .build();

        TourEntity result = mapper.toEntity(tour);

        assertEquals(tour.getTourId(), result.getTour_id());
        assertEquals(tour.getName(), result.getName());
        assertEquals(tour.getFrom(), result.getFromLocation());
        assertEquals(tour.getTo(), result.getToLocation());
        assertEquals(tour.getDescription(), result.getDescription());
        assertEquals(tour.getEstimatedTime(), result.getEstimatedTime());
        assertEquals(tour.getRouteInformation(), result.getRouteInformation());
        assertEquals(tour.getTransportType(), result.getTransportType());
        assertEquals(tour.getTourDistance(), result.getTourDistance());
    }

    @Test
    public void testFromEntityWithNull() {
        TourEntity entity = null;

        Tour result = mapper.fromEntity(entity);

        assertNotNull(result);
        assertNull(result.getTourId());
        assertNull(result.getName());
        assertNull(result.getFrom());
        assertNull(result.getTo());
        assertNull(result.getDescription());
        assertNull(result.getEstimatedTime());
        assertNull(result.getRouteInformation());
        assertNull(result.getTransportType());
        assertNull(result.getTourDistance());
    }

    @Test
    public void testToEntityWithNull() {
        Tour tour = Tour.builder()
                .tourId(null)
                .name(null)
                .from(null)
                .to(null)
                .description(null)
                .estimatedTime(null)
                .routeInformation(null)
                .transportType(null)
                .tourDistance(null)
                .build();

        TourEntity result = mapper.toEntity(tour);

        assertNotNull(result);
        assertNull(result.getTour_id());
        assertNull(result.getName());
        assertNull(result.getFromLocation());
        assertNull(result.getToLocation());
        assertNull(result.getDescription());
        assertNull(result.getEstimatedTime());
        assertNull(result.getRouteInformation());
        assertNull(result.getTransportType());
        assertNull(result.getTourDistance());
    }
}
