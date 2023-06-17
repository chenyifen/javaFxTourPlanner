package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.dto.MapQuestRoute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MapQuestService {

    public static final String MAPQUEST_STATIC_MAP_URL = "https://www.mapquestapi.com/staticmap/v5/map?"
            + "key=%s"
            + "&size=%dx%d"
            + "&locations=%s,%s|marker-1||%s,%s|marker-2||"
            + "&center=%f,%f"
            + "&defaultMarker=none"
            + "&zoom=%d"
            + "&session=AM5kLt8vofNAS7W-K1pUwZMGlKGLuGze9TJ02nX2MhOs16jI9fwSpb0oE-6qJgVDIqVDR-66yYA7wH35isuq9ZbFb7nLLoguLw";
    private final String apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    public MapQuestService(@Value("${mapquest.api.key}") String apiKey, RestTemplateBuilder restTemplateBuilder) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplateBuilder.build();
    }

    public MapQuestRoute getRoute(String from, String to) {
        Map<String, String> params = new HashMap<>();
        params.put("key", this.apiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"locations\":[{\"street\":\"" + from + "\"},{\"street\":\"" + to + "\"}]}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://www.mapquestapi.com/directions/v2/route?key={key}", HttpMethod.POST, request, String.class, params);

        log.info("MapQuest request :" + request);

        String json = response.getBody();
        log.info("MapQuest response :" + json);
        MapQuestRoute route = new MapQuestRoute();
        route.setDistance(0);
        route.setMapUrl("");
        route.setTime(0);
        // 解析JSON数据
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode routeNode = rootNode.get("route");
            if (routeNode != null) {
                JsonNode legsNode = routeNode.get("legs");
                if(legsNode != null) {
                    JsonNode firstLegNode = legsNode.get(0);
                    JsonNode maneuversNode = firstLegNode.get("maneuvers");
                    JsonNode firstManeuverNode = maneuversNode.get(0);

                    route.setDistance(firstManeuverNode.get("distance").doubleValue());
                    route.setMapUrl(firstManeuverNode.get("mapUrl").textValue().replace("size=225,160", "size=450,250").replace("zoom=16", "zoom=10"));
                    route.setTime(firstManeuverNode.get("time").intValue());
                }
                return route;
            }
        } catch (JsonProcessingException e) {
            return route;
//            throw new RuntimeException("Failed to parse JSON response from MapQuest API", e);
        }
        return route;
    }

    public Image getStaticMapImage(String url) {
        byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
        return new Image(new ByteArrayInputStream(imageBytes));
    }

}
