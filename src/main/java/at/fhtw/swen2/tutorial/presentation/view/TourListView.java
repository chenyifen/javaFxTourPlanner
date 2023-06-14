package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.NewTourViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourDetailViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.TourService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
@Slf4j
public class TourListView implements Initializable {

    @Autowired
    private TourService TourService;
    @Autowired
    private SearchView searchView;
    @Autowired
    private TourListViewModel tourListViewModel;

    @FXML
    private Text feedbackText;
    @FXML
    private TextField nameTextField;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
//        nameTextField.textProperty().bindBidirectional(tourListViewModel.nameProperty());
    }

    public void add(ActionEvent event) {
       // TODO
    }

    public void delete(ActionEvent event) {
        // TODO
    }

    public void edit(ActionEvent event) {
        // TODO
    }
}
