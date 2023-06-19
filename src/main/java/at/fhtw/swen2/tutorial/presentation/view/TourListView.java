package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.TourListChangeListener;
import at.fhtw.swen2.tutorial.presentation.TourListChangePublisher;
import at.fhtw.swen2.tutorial.presentation.TourSelectionPublisher;
import at.fhtw.swen2.tutorial.presentation.viewmodel.RouteViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
@Slf4j
public class TourListView implements Initializable , TourListChangeListener{

    List<String> tourNames = FXCollections.observableArrayList();
    @Autowired
    private TourService TourService;
    @Autowired
    private SearchView searchView;
    @FXML
    private ListView<Tour> listView;
    @Autowired
    private TourListViewModel tourListViewModel;
    @Autowired
    private RouteViewModel routeViewModel;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        TourListChangePublisher.getInstance().register(this);
        TourSelectionPublisher.getInstance().register(tourListViewModel);
        TourSelectionPublisher.getInstance().register(routeViewModel);
        ObservableList<Tour> items = tourListViewModel.getTourListItems();
        listView.setItems(items);
        listView.setCellFactory(param -> new ListCell<Tour>() {
            @Override
            protected void updateItem(Tour tour, boolean empty) {
                super.updateItem(tour, empty);
                if (empty || tour == null || tour.getName() == null) {
                    setText(null);
                } else {
                    setText(tour.getName());
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TourSelectionPublisher.getInstance().notify(newValue);
        });

        searchView.loadData();
        listView.requestFocus();
        listView.getSelectionModel().selectFirst();
    }

    @FXML
    public void delete() {
        Tour tour = listView.getSelectionModel().getSelectedItem();
        Alert alert;
        if (tour != null) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Tour");
            alert.setHeaderText("Delete Tour");
            alert.setContentText("Are you sure you want to delete " + tour.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tourListViewModel.deleteItem(tour);
            }
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Tour Selected");
            alert.setHeaderText("No Tour Selected");
            alert.setContentText("Please select a Tour before deleting.");
            alert.showAndWait();
        }

    }

    @FXML
    public void add() {
        TourEditDialogView dialog = new TourEditDialogView();
        Optional<Tour> result = dialog.showAndWait();
        if (result.isPresent()) {
            listView.getItems().clear();
            tourListViewModel.addItem(result.get());
        }

    }

    @FXML
    public void edit() {
        Tour tour = listView.getSelectionModel().getSelectedItem();
        if (tour != null) {
            TourEditDialogView dialog = new TourEditDialogView(tour.getTourId(),
                    tour.getName(),
                    tour.getDescription(),
                    tour.getFrom(),
                    tour.getTo(),
                    tour.getTransportType());
            Optional<Tour> result = dialog.showAndWait();
            if (result.isPresent()) {
                tourListViewModel.edit(result.get());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Tour Selected");
            alert.setHeaderText("No Tour Selected");
            alert.setContentText("Please select a Tour before editing.");
            alert.showAndWait();
        }
    }

    @Override
    public void newTourAdded() {
        listView.getItems().clear();
        tourListViewModel.initList(true);
    }
}
