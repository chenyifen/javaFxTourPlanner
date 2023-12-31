package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.TourSelectionListener;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourDetailViewModel implements TourSelectionListener {

    @Autowired
    TourService TourService;
    @Autowired
    private TourListViewModel tourListViewModel;
    private Tour selectedItem = null;




    public ObservableList<Tour> getTourListItems() {
        return tourListViewModel.getTourListItems();
    }

    public void addItem(Tour tour) {
        tourListViewModel.addItem(tour);
    }

    public void clearItems(){
        tourListViewModel.clearItems();
    }

    public void initList(){
        tourListViewModel.initList(false);
    }

    public void filterList(String searchText){
        tourListViewModel.filterList(searchText);
    }

    @Override
    public void tourSelected(Tour tour) {
        selectedItem = tour;
        //TODO
    }
}
