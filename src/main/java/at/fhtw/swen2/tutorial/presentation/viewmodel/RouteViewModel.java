package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.TourSelectionListener;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteViewModel implements TourSelectionListener {


    @Autowired
    private TourListViewModel tourListViewModel;
    private Tour selectedItem = null;
    private final StringProperty url = new SimpleStringProperty("");


    @Override
    public void tourSelected(Tour tour) {
        selectedItem = tour;
        if (tour != null) {
            url.set(tour.getRouteInformation());
        } else
            url.set("");
    }

    public StringProperty getUrl() {
        return url;
    }
}
