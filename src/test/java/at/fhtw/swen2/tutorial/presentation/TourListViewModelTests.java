package at.fhtw.swen2.tutorial.presentation;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourListViewModelTests {

    @Autowired
    private TourListViewModel tourListViewModel;

    @Autowired
    private TourService tourService;

    @Test
    @Transactional
    void testAddItem() {
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

        tourListViewModel.addItem(tour);

        assertTrue(tourListViewModel.getTourListItems().contains(tour));
    }

    @Test
    void testClearItems() {
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

        tourListViewModel.addItem(tour);

        tourListViewModel.clearItems();

        assertTrue(tourListViewModel.getTourListItems().isEmpty());
    }

    @Test
    void testFilterList() {
        Tour tour1 = Tour.builder()
                .name("TestTour1")
                .transportType("Bike")
                .tourDistance("1")
                .routeInformation("Route A")
                .to("Street A")
                .from("Street B")
                .estimatedTime("1 hour")
                .description("My Test Tour1")
                .build();

        Tour tour2 = Tour.builder()
                .name("TestTour2")
                .transportType("Bike")
                .tourDistance("1")
                .routeInformation("Route B")
                .to("Street A")
                .from("Street B")
                .estimatedTime("1 hour")
                .description("My Test Tour2")
                .build();

        tourListViewModel.addItem(tour1);
        tourListViewModel.addItem(tour2);

        tourListViewModel.filterList("test");


        tourListViewModel.filterList("a");

        assertTrue(tourListViewModel.getTourListItems().contains(tour1));
        assertTrue(tourListViewModel.getTourListItems().contains(tour2));
    }

    @Test
    void testDeleteItem() {
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

        tourListViewModel.addItem(tour);
        tourListViewModel.deleteItem(tour);

        assertFalse(tourListViewModel.getTourListItems().contains(tour));
    }

    @Test
    void testEdit() {
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

        tourListViewModel.addItem(tour);

        Tour newTour = Tour.builder()
                .name("TestTour1")
                .transportType("Bike")
                .tourDistance("1")
                .routeInformation("Route 1")
                .to("Street A")
                .from("Street B")
                .estimatedTime("1 hour")
                .description("My Test Tour1")
                .build();


        tourListViewModel.edit(newTour);

        assertTrue(tourListViewModel.getTourListItems().contains(newTour));
        assertFalse(tourListViewModel.getTourListItems().contains(tour));
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

        tourListViewModel.tourSelected(tour);

        assertEquals(tour, tourListViewModel.getSelectedTour());
    }
}
