package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.TourLogSelectionPublisher;
import at.fhtw.swen2.tutorial.presentation.TourSelectionPublisher;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogViewModel;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

@Component
@Slf4j
@Scope("prototype")
public class TourLogView implements Initializable {

    @Autowired
    public TourLogViewModel tourLogViewModel;
    @Autowired
    public TourListViewModel tourListViewModel;

    @FXML
    public TableView tableView = new TableView<>();
    @FXML
    private VBox dataContainer;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        TourSelectionPublisher.getInstance().register(tourLogViewModel);
        TourLogSelectionPublisher.getInstance().register(tourLogViewModel);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<TourLog, UUID> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("logId"));
        idColumn.setVisible(false);
        TableColumn<TourLog, String> name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<TourLog, String> comment = new TableColumn("Comment");
        comment.setCellValueFactory(new PropertyValueFactory("comment"));
        TableColumn<TourLog, String> difficulty = new TableColumn("Difficulty");
        difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));
        TableColumn<TourLog, String> totalTime = new TableColumn("TotalTime");
        totalTime.setCellValueFactory(new PropertyValueFactory("totalTime"));
        TableColumn<TourLog, String> rating = new TableColumn("Rating");
        rating.setCellValueFactory(new PropertyValueFactory("rating"));

        tableView.getColumns().addAll(idColumn, name, comment, difficulty, totalTime, rating);

        dataContainer.getChildren().add(tableView);
        tourLogViewModel.initList();
        tableView.setItems(tourLogViewModel.getTourLogItems());


        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                log.info("selected row : new value  = " + newValue.toString());
                log.info("selected row : new value id = " + ((TourLog) newValue).getLogId());
                log.info("selected row : new value name= " + ((TourLog) newValue).getName());
                TourLogSelectionPublisher.getInstance().notify((TourLog) newValue);
            }else{
                log.info("selected row : new value  NULL ");

            }
        });
    }


    @FXML
    public void delete() {
        TourLog tourLog = tourLogViewModel.getSelectedTourLog();
        Alert alert;
        if (tourLog != null) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Tour Log");
            alert.setHeaderText("Delete Tour Log");
            alert.setContentText("Are you sure you want to delete: " + tourLog.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tourLogViewModel.deleteItem(tourLog);
            }
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Tour Log Selected");
            alert.setHeaderText("No Tour Log Selected");
            alert.setContentText("Please select a Tour Log before deleting.");
            alert.showAndWait();
        }

    }

    @FXML
    public void add() {
        TourLogEditDialogView dialog = new TourLogEditDialogView(tourListViewModel.getSelectedTour().getTourId());
        Optional<TourLog> result = dialog.showAndWait();
        if (result.isPresent()) {
            tourLogViewModel.addItem(result.get());
        }
    }

    @FXML
    public void edit() {
        TourLog tourLog = tourLogViewModel.getSelectedTourLog();
        if (tourLog != null) {
            TourLogEditDialogView dialog = new TourLogEditDialogView(
                    tourLog.getLogId(),
                    tourLog.getName(),
                    tourLog.getComment(),
                    tourLog.getDifficulty(),
                    tourLog.getTotalTime(),
                    tourLog.getRating(),
                    tourListViewModel.getSelectedTour().getTourId()
            );
            Optional<TourLog> result = dialog.showAndWait();
            if (result.isPresent()) {
                tourLogViewModel.edit(result.get());
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
