package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.TourSelectionPublisher;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourDetailViewModel;
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
public class TourDetailView implements Initializable {

    @Autowired
    public TourDetailViewModel tourDetailViewModel;

    @FXML
    public TableView tableView = new TableView<>();
    @FXML
    private VBox dataContainer;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        TourSelectionPublisher.getInstance().register(tourDetailViewModel);
        tableView.setItems(tourDetailViewModel.getTourListItems());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory("tourId"));
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn description = new TableColumn("Description");
        description.setCellValueFactory(new PropertyValueFactory("description"));
        TableColumn from = new TableColumn("From");
        from.setCellValueFactory(new PropertyValueFactory("from"));
        TableColumn to = new TableColumn("To");
        to.setCellValueFactory(new PropertyValueFactory("to"));
        TableColumn transportType = new TableColumn("TransportType");
        transportType.setCellValueFactory(new PropertyValueFactory("transportType"));
        TableColumn tourDistance = new TableColumn("TourDistance");
        tourDistance.setCellValueFactory(new PropertyValueFactory("tourDistance"));
        TableColumn estimatedTime = new TableColumn("EstimatedTime");
        estimatedTime.setCellValueFactory(new PropertyValueFactory("estimatedTime"));
        TableColumn routeInformation = new TableColumn("routeInformation");
        routeInformation.setCellValueFactory(new PropertyValueFactory("routeInformation"));


        tableView.getColumns().addAll(id, name, description, from, to, transportType, tourDistance, estimatedTime, routeInformation);

        dataContainer.getChildren().add(tableView);
        tourDetailViewModel.initList();
    }

}
