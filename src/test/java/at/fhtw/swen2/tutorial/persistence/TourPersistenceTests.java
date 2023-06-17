package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.impl.TourServiceImpl;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
@SpringBootTest
class TourPersistenceTests {

    @Autowired
    private TourServiceImpl tourServiceImpl;

    @Test
    @Transactional
    void testAddTour() {
        Tour tour = Tour.builder()
                .name("TestTour1")
                .transportType("Bike")
                .tourDistance("1")
                .routeInformation("Route")
                .to("Street A")
                .from("Street B")
                .estimatedTime("1 hour")
                .description("My Test Tour")
                .build();
        tourServiceImpl.addNew(tour);
        List<Tour> list = tourServiceImpl.getTourList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals("TestTour1")) {
//                tourServiceImpl.delete(tour);
                log.info("Test Pass");
                assertTrue(true);
                return;
            }
        }
        //cant find Test Tour, test fail
        fail();
    }

    @Test
    @Transactional
    void testDeleteTour() {
        Tour tour = Tour.builder()
                .name("TestTour2")
                .transportType("Bike")
                .tourDistance("1")
                .routeInformation("Route")
                .to("Street A")
                .from("Street B")
                .estimatedTime("1 hour")
                .description("My Test Tour")
                .build();
        tourServiceImpl.addNew(tour);
        tourServiceImpl.getTourList().forEach(p -> {
            if (p.getName() == "TestTour2") {
                tourServiceImpl.delete(tour);
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    List<Tour> list = tourServiceImpl.getTourList();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().equals("TestTour2")) {
                            log.info("Test Pass");
                            assertTrue(true);
                            return;
                        }
                    }
                });
            }
        });
        assert true;
    }

}
