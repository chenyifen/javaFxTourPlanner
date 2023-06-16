package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.TourSelectionPublisher;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
public class TourListView implements Initializable {

    List<String> tourNames = FXCollections.observableArrayList();
    @Autowired
    private TourService TourService;
    @Autowired
    private SearchView searchView;
    @FXML
    private ListView<Tour> listView;
    @Autowired
    private TourListViewModel tourListViewModel;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        tourListViewModel.initList(false);
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
        TourDialogView dialog = new TourDialogView();
        Optional<Tour> result = dialog.showAndWait();
        if (result.isPresent()) {
            tourListViewModel.addItem(result.get());
        }
    }

    @FXML
    public void edit() {
        Tour tour = listView.getSelectionModel().getSelectedItem();
        if (tour != null) {
            TourDialogView dialog = new TourDialogView(tour.getId(),
                    tour.getName(),
                    tour.getDescription(),
                    tour.getFrom(),
                    tour.getTo(),
                    tour.getTransportType());
            Optional<Tour> result = dialog.showAndWait();
            if (result.isPresent()) {
                tourListViewModel.edit(tour);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Tour Selected");
            alert.setHeaderText("No Tour Selected");
            alert.setContentText("Please select a Tour before editing.");
            alert.showAndWait();
        }
    }
}
