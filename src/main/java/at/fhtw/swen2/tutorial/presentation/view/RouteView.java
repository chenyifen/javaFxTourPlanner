package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.ApplicationContextProvider;
import at.fhtw.swen2.tutorial.presentation.viewmodel.RouteViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.MapQuestService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class RouteView {
    @Autowired
    RouteViewModel routeViewModel;
    String url;
    @Autowired
    private MapQuestService mapQuestService;
    @Autowired
    private TourListViewModel tourListViewModel;
    @FXML
    private ImageView mapImageView;

    public void initialize() {
        log.info("Route View init");
        tourListViewModel.initList(false);
        this.mapQuestService = ApplicationContextProvider.getApplicationContext().getBean(MapQuestService.class);
        routeViewModel.getUrl().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateImage(newValue);
            }
        });
    }

    public void updateImage(String newUrl) {
        log.info("Update Image with url:" + newUrl);
        url = newUrl;
        if (url.startsWith("http")) {
            Image image = mapQuestService.getStaticMapImage(url);
            if (image != null) {
                log.info("getImage success");
                mapImageView.setImage(image);
            }
        }
    }
}
