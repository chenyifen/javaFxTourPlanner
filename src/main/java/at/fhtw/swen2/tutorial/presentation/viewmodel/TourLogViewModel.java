package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.TourLogSelectionListener;
import at.fhtw.swen2.tutorial.presentation.TourSelectionListener;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TourLogViewModel implements TourSelectionListener, TourLogSelectionListener {
    private final List<TourLog> masterData = new ArrayList<>();
    private final ObservableList<TourLog> tourLogItems = FXCollections.observableArrayList();
    @Autowired
    TourLogService tourLogService;
    @Autowired
    TourListViewModel tourListViewModel;
    Tour selectedTour = null;
    TourLog selectedTourLog = null;

    public ObservableList<TourLog> getTourLogItems() {
        tourLogItems.forEach(p -> {
            log.info("Tour Log: id =  "+p.getLogId());
        });
        return tourLogItems;
    }

    public TourLog getSelectedTourLog() {
        return selectedTourLog;
    }

    public void addItem(TourLog tourLog) {
        tourLogService.addNew(tourLog);
        initList();
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
        tourLogService.getTourLogList().forEach(p -> {
            masterData.add(p);
        });
        tourLogService.getTourLogListByTour(selectedTour).forEach(p -> {
            tourLogItems.add(p);
        });
    }

    public void filterList(String searchText) {
        //TODO
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
        if (tourLog != null) {
            tourLogService.update(tourLog);
            initList();
        }
    }


    @Override
    public void tourSelected(Tour tour) {
        selectedTour = tour;
        initList();
    }

    @Override
    public void tourLogSelected(TourLog tourLog) {
        if (tourLog != null) {
            log.info("tour log Selected: " + tourLog.toString());
            selectedTourLog = tourLog;
        }
    }
}
