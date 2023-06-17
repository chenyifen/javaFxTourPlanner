package at.fhtw.swen2.tutorial.utils;

import at.fhtw.swen2.tutorial.service.MapQuestService;
import at.fhtw.swen2.tutorial.service.dto.MapQuestRoute;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;

@SpringBootTest
public class MapQuestServiceTests {

    @Autowired
    private MapQuestService mapQuestService;

    private static final String FROM_ADDRESS = "Hauptstraße 1, 4020 Linz";
    private static final String TO_ADDRESS = "Landstraße 1, 1010 Wien";
    private static final String LONG_DISTANCE_FROM = "New York";
    private static final String LONG_DISTANCE_TO = "San Francisco";

    @BeforeEach
    void setup() {
        Assertions.assertNotNull(mapQuestService);
    }

    @Test
    void testGetRouteValid() {
        MapQuestRoute route = mapQuestService.getRoute(FROM_ADDRESS, TO_ADDRESS);
        Assertions.assertNotNull(route);
        Assertions.assertTrue(route.getTime() > 0);
        Assertions.assertTrue(route.getDistance() > 0);
        Assertions.assertNotNull(route.getMapUrl());
        Assertions.assertNotEquals(route.getMapUrl(), "");
        javafx.scene.image.Image image = mapQuestService.getStaticMapImage(route.getMapUrl());
        Assertions.assertNotNull(image);
    }

    @Test
    void testGetRouteEmptyInput() {
        MapQuestRoute route = mapQuestService.getRoute("", "");
        Assertions.assertNotNull(route);
        Assertions.assertEquals(route.getTime(), 0);
        Assertions.assertEquals(route.getDistance(), 0);
        Assertions.assertEquals(route.getMapUrl(), "");
    }

    @Test
    void testGetRouteInvalid() {
        MapQuestRoute route = mapQuestService.getRoute("Unknown A", "Unknown B");
        Assertions.assertNotNull(route);
    }

    @Test
    void testGetRouteLongDistance() {
        MapQuestRoute route = mapQuestService.getRoute(LONG_DISTANCE_FROM, LONG_DISTANCE_TO);
        Assertions.assertNotNull(route);
        Assertions.assertTrue(route.getTime() > 0);
        Assertions.assertTrue(route.getDistance() > 0);
        Assertions.assertNotNull(route.getMapUrl());
        Assertions.assertNotEquals(route.getMapUrl(), "");
        javafx.scene.image.Image image = mapQuestService.getStaticMapImage(route.getMapUrl());
        Assertions.assertNotNull(image);
    }

    @Test
    void testGetStaticMapImage() {
        MapQuestRoute route = mapQuestService.getRoute(FROM_ADDRESS, TO_ADDRESS);
        Image image = mapQuestService.getStaticMapImage(route.getMapUrl());
        Assertions.assertNotNull(image);
    }

}
