package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.impl.TourLogServiceImpl;
import at.fhtw.swen2.tutorial.service.impl.TourServiceImpl;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Slf4j
class TourLogPersistenceTests {

    @Autowired
    private TourLogServiceImpl tourLogService;

    @Autowired
    private TourServiceImpl tourService;

    @Test
    @Transactional
    void testAddTourLog() {
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
        List<Tour> tourList = tourService.getTourList();
        for (int i = 0; i < tourList.size(); i++) {
            if (tourList.get(i).getName().equals("TestTour1")) {
                tour.setTourId(tourList.get(i).getTourId());
            }
        }

        TourLog tourLog = TourLog.builder()
                .name("TestTourLog")
                .comment("My TestTourLog")
                .difficulty("3")
                .totalTime("2 hours")
                .rating("5")
                .tour(tour)
                .build();
        tourLogService.addNew(tour, tourLog);

        List<TourLog> list = tourLogService.getTourLogList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals("TestTourLog")) {
                log.info("Test Pass");
                assertTrue(true);
                return;
            }
        }
        fail("Can't find TestTourLog");
    }

    @Test
    @Transactional
    void testDeleteTourLog() {
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
        List<Tour> tourList = tourService.getTourList();
        for (int i = 0; i < tourList.size(); i++) {
            if (tourList.get(i).getName().equals("TestTour1")) {
                tour.setTourId(tourList.get(i).getTourId());
            }
        }

        TourLog tourLog = TourLog.builder()
                .name("TestTourLog")
                .comment("My TestTourLog")
                .difficulty("3")
                .totalTime("2 hours")
                .rating("5")
                .tour(tour)
                .build();
        tourLogService.addNew(tour, tourLog);

        List<TourLog> list = tourLogService.getTourLogList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals("TestTourLog")) {
                tourLogService.delete(list.get(i));
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                List<TourLog> newList = tourLogService.getTourLogList();
                for (int j = 0; j < newList.size(); j++) {
                    if (newList.get(j).getName().equals("TestTourLog")) {
                        fail("Test failed, can't delete TestTourLog");
                    }
                }
                log.info("Test pass");
                assertTrue(true);

                return;
            }
        }
        fail("Test failed, can't add TestTourLog");
    }

}
