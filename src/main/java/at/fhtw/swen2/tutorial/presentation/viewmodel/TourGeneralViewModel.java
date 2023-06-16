package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.TourSelectionListener;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourGeneralViewModel implements TourSelectionListener {

    @Autowired
    TourService TourService;
    @Autowired
    private TourListViewModel tourListViewModel;
    private final ObservableList<Tour> tourListItems = FXCollections.observableArrayList();
    private Tour selectedItem = null;





    public ObservableList<Tour> getTourListItems() {
        return tourListItems;
    }


    public void initList(){
        tourListViewModel.initList(false);
        tourListItems.clear();
        if(selectedItem != null) {
            tourListItems.add(selectedItem);
        }
    }


    @Override
    public void tourSelected(Tour tour) {
        selectedItem = tour;
        initList();
    }
}
