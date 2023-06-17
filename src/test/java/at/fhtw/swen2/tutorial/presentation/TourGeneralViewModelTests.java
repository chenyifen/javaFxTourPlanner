package at.fhtw.swen2.tutorial.presentation;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourGeneralViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourGeneralViewModelTests {

    @Autowired
    private TourGeneralViewModel tourGeneralViewModel;

    @Autowired
    private TourListViewModel tourListViewModel;

    @Autowired
    private TourService tourService;

    @Test
    void testGetTourGeneralItem() {
        tourGeneralViewModel.tourSelected(null);
        ObservableList<Tour> tourGeneralItem = tourGeneralViewModel.getTourGeneralItem();

        assertNotNull(tourGeneralItem);
        assertTrue(tourGeneralItem.isEmpty());
    }

    @Test
    void testInitListWhenSelectedTourIsNull() {
        tourGeneralViewModel.initList();

        assertTrue(tourGeneralViewModel.getTourGeneralItem().isEmpty());
    }

    @Test
    void testInitListWhenSelectedTourIsNotNull() {
        Tour tour = Tour.builder()
                .name("TestTour")
                .transportType("Bike")
                .tourDistance("1")
                .routeInformation("Route")
                .to("Street A")
                .from("Street B")
                .estimatedTime("1 hour")
                .description("My Test Tour")
                .build();

        tourService.addNew(tour);

        tourListViewModel.initList(true);
        tourListViewModel.filterList("");

        tourGeneralViewModel.tourSelected(tour);
        tourGeneralViewModel.initList();

        assertTrue(tourGeneralViewModel.getTourGeneralItem().contains(tour));
    }

    @Test
    void testTourSelected() {
        Tour tour = Tour.builder()
                .name("TestTour")
                .transportType("Bike")
                .tourDistance("1")
                .routeInformation("Route")
                .to("Street A")
                .from("Street B")
                .estimatedTime("1 hour")
                .description("My Test Tour")
                .build();

        tourGeneralViewModel.tourSelected(tour);

        assertEquals(tour, tourGeneralViewModel.getTourGeneralItem().get(0));
    }
}
