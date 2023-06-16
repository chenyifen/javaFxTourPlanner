package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.TourSelectionPublisher;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class TourLogView implements Initializable {

    @Autowired
    public TourLogViewModel tourLogViewModel;

    @FXML
    public TableView tableView = new TableView<>();
    @FXML
    private VBox dataContainer;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        TourSelectionPublisher.getInstance().register(tourLogViewModel);
        tableView.setItems(tourLogViewModel.getTourLogItems());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn comment = new TableColumn("Comment");
        comment.setCellValueFactory(new PropertyValueFactory("comment"));
        TableColumn difficulty = new TableColumn("Difficulty");
        difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));
        TableColumn totalTime = new TableColumn("TotalTime");
        totalTime.setCellValueFactory(new PropertyValueFactory("totalTime"));
        TableColumn rating = new TableColumn("Rating");
        rating.setCellValueFactory(new PropertyValueFactory("rating"));

        tableView.getColumns().addAll(name, comment, difficulty, totalTime, rating);

        dataContainer.getChildren().add(tableView);
        tourLogViewModel.initList();
    }

}
