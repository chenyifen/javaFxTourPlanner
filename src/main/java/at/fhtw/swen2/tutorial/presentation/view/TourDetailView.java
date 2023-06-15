package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
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
    public TourListViewModel TourListViewModel;

    @FXML
    public TableView tableView = new TableView<>();
    @FXML
    private VBox dataContainer;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        tableView.setItems(TourListViewModel.getTourListItems());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn description = new TableColumn("Description");
        name.setCellValueFactory(new PropertyValueFactory("description"));
        TableColumn from = new TableColumn("From");
        name.setCellValueFactory(new PropertyValueFactory("from"));
        TableColumn to = new TableColumn("To");
        name.setCellValueFactory(new PropertyValueFactory("to"));
        TableColumn transportType = new TableColumn("TransportType");
        name.setCellValueFactory(new PropertyValueFactory("transportType"));
        TableColumn tourDistance = new TableColumn("TourDistance");
        name.setCellValueFactory(new PropertyValueFactory("tourDistance"));
        TableColumn estimatedTime = new TableColumn("EstimatedTime");
        name.setCellValueFactory(new PropertyValueFactory("estimatedTime"));
        TableColumn routeInformation = new TableColumn("routeInformation");
        name.setCellValueFactory(new PropertyValueFactory("routeInformation"));


        tableView.getColumns().addAll(id, name, description, from, to, transportType, tourDistance, estimatedTime, routeInformation);

        dataContainer.getChildren().add(tableView);
        TourListViewModel.initList();
    }

}