package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.TourSelectionListener;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
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
public class TourListViewModel implements TourSelectionListener {

    @Autowired
    TourService tourService;


    private final List<Tour> masterData = new ArrayList<>();
    private final ObservableList<Tour> tourListItems = FXCollections.observableArrayList();
    private Tour selectedItem = null;

    public ObservableList<Tour> getTourListItems() {
        return tourListItems;
    }

    public void addItem(Tour tour) {
        tourService.addNew(tour);
        tourListItems.add(tour);
        initList(true);
    }

    public void clearItems() {
        tourListItems.clear();
    }

    //TODO for debug only
    public void printStack() {
        // Get the current thread's stack trace
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // Log the stack trace elements as a single string
        StringBuilder sb = new StringBuilder();
        sb.append("Call stack:\n");
        for (StackTraceElement element : stackTrace) {
            sb.append("  ").append(element.getClassName())
                    .append(".").append(element.getMethodName())
                    .append("(").append(element.getFileName())
                    .append(":").append(element.getLineNumber())
                    .append(")\n");
        }
        log.info(sb.toString());
    }

    public void initList(Boolean dataChanged) {
//        printStack();
        if(!dataChanged && tourListItems.size() > 0){
            //already init.
            return;
        }
        tourListItems.clear();
        masterData.clear();
        tourService.getTourList().forEach(p -> {
            log.info("initList: p.id = " +p.getTourId()+",p.name="+p.getName());
            tourListItems.add(p);
            masterData.add(p);
        });

        log.info("initList : tourListItems.size = " + tourListItems.size());
    }

    public Tour getSelectedTour() {
        return selectedItem;
    }

    public void filterList(String searchText) {
        Task<List<Tour>> task = new Task<>() {
            @Override
            protected List<Tour> call() throws Exception {
                updateMessage("Loading data");
                log.info("Tour to string : "+masterData.get(0).toString());
                return masterData
                        .stream()
                        .filter(value -> value.toString().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());
            }
        };

        task.setOnSucceeded(event -> {
            tourListItems.setAll(task.getValue());
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    public void deleteItem(Tour tour) {
//        if (selectedItem != null && selectedItem.getTourId() == tour.getTourId()) {
//            selectedItem = null;
//        }
        if (tour != null) {
            tourListItems.remove(tour);
            tourService.delete(tour);
        }
    }


    public void edit(Tour tour) {
        if (tour != null) {
            for(int i = 0; i < tourListItems.size(); i++)
            {
                if(tourListItems.get(i).getTourId() == tour.getTourId()){
                    log.info("update Tour: " + tourListItems.get(i).getName()+" at index:"+i );

                    tourListItems.set(i , tour);
                }
            }
            tourService.update(tour);
        }
    }


    @Override
    public void tourSelected(Tour tour) {
        selectedItem = tour;
    }
}
