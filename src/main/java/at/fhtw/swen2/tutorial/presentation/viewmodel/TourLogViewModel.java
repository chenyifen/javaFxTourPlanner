package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.TourSelectionListener;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourLogViewModel implements TourSelectionListener {
    private static final Logger LOG = LogManager.getLogger(TourLogViewModel.class);
    private final List<TourLog> masterData = new ArrayList<>();
    private final ObservableList<TourLog> tourLogItems = FXCollections.observableArrayList();
    @Autowired
    TourLogService tourLogService;
    @Autowired
    TourListViewModel tourListViewModel;
    Tour selectedTour = null;

    public ObservableList<TourLog> getTourLogItems() {
        return tourLogItems;
    }

    public void addItem(TourLog tourLog) {
        tourLogItems.add(tourLog);
        masterData.add(tourLog);
    }

    public void clearItems() {
        tourLogItems.clear();
    }

    public void initList() {
        tourListViewModel.initList(false);
        if (selectedTour == null) {
            return;
        }
        tourLogItems.clear();
        tourLogService.getTourLogListByTour(selectedTour).forEach(p -> {
            addItem(p);
        });
    }

    public void filterList(String searchText) {
        Task<List<TourLog>> task = new Task<>() {
            @Override
            protected List<TourLog> call() throws Exception {
                updateMessage("Loading data");
                return masterData
                        .stream()
                        .filter(value -> value.toString().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());
            }
        };

        task.setOnSucceeded(event -> {
            tourLogItems.setAll(task.getValue());
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    public void deleteItem(TourLog tourLog) {
        if (tourLog != null) {
            tourLogService.delete(tourLog);
            initList();
        }
    }


    public void edit(TourLog tourLog) {

    }


    @Override
    public void tourSelected(Tour tour) {
        if(tour != null) {
            LOG.info("tour Selected: " + tour.toString());
        }
        selectedTour = tour;
        initList();
    }
}
