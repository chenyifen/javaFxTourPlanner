package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TourDialogView extends Dialog<Tour> {

    private final TextField nameField;
    private final TextArea descriptionField;
    private final TextField fromField;
    private final TextField toField;
    private final TextField transportTypeField;

    public TourDialogView() {
        this(-1L, "", "", "", "", null);
    }

    public TourDialogView(Long id, String name, String description, String from, String to, String transportType) {
        log.debug("TourDialogView");
        setTitle("Add Tour");
        setHeaderText("Please enter tour details.");

        // Set the button types
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the name label and field
        Label nameLabel = new Label("Name:");
        nameField = new TextField(name);
        nameField.setPromptText("Enter tour name.");

        // Create the description label and field
        Label descriptionLabel = new Label("Description:");
        descriptionField = new TextArea(description);
        descriptionField.setPromptText("Enter tour description.");
        descriptionField.setWrapText(true);
        descriptionField.setMaxHeight(Double.MAX_VALUE);

        // Create the from label and field
        Label fromLabel = new Label("From:");
        fromField = new TextField(from);
        fromField.setPromptText("Enter journey start location.");

        // Create the to label and field
        Label toLabel = new Label("To:");
        toField = new TextField(to);
        toField.setPromptText("Enter journey end location.");

        Label transportTypeLabel = new Label("Transport Type:");
        transportTypeField = new TextField(transportType);
        transportTypeField.setPromptText("Select transport type.");

        // Create the grid pane and add the fields to it
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.addRow(0, nameLabel, nameField);
        grid.addRow(1, descriptionLabel, descriptionField);
        grid.addRow(2, fromLabel, fromField);
        grid.addRow(3, toLabel, toField);
        grid.addRow(4, transportTypeLabel, transportTypeField);


        // Set the content of the dialog pane to the grid pane
        getDialogPane().setContent(grid);

        // Convert the result to a tour model when the OK button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Validate input
                if (nameField.getText().isEmpty() || descriptionField.getText().isEmpty()
                        || fromField.getText().isEmpty() || toField.getText().isEmpty()
                        || transportTypeField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all fields.");
                    alert.showAndWait();
                    return null;
                } else {
                    //TODO api get
                    Tour tour = Tour.builder()
                            .id(id)
                            .name(nameField.getText())
                            .transportType(transportTypeField.getText())
                            .description(descriptionField.getText())
                            .from(fromField.getText())
                            .to(toField.getText())
                            .build();
                    log.info("Edit Result: tour = " +tour.getName() +", trans = " +tour.getTransportType());
                    return tour;
                }
            }
            return null;
        });
    }

}
