package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class TourLogEditDialogView extends Dialog<TourLog> {

    private final TextField nameField;
    private final TextArea commentField;
    private final TextField difficultyField;
    private final TextField totalTimeField;
    private final TextField ratingField;

    public TourLogEditDialogView(Long tourId) {
        this(null, "", "", "", "", "", tourId);
    }

    public TourLogEditDialogView(UUID logId, String name, String comment, String difficulty, String totalTime, String rating, Long tourId) {
        log.debug("TourLogDialogView");
        if (name.equals("")) {
            setTitle("Add Tour Log");
        }else{
            setTitle("Edit Tour Log");
        }
        setHeaderText("Please enter tour log details.");

        // Set the button types
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the name label and field
        Label nameLabel = new Label("Name:");
        nameField = new TextField(name);
        nameField.setPromptText("Enter tour log name.");

        // Create the description label and field
        Label descriptionLabel = new Label("Comment:");
        commentField = new TextArea(comment);
        commentField.setPromptText("Enter tour Comment.");
        commentField.setWrapText(true);
        commentField.setMaxHeight(Double.MAX_VALUE);

        // Create the from label and field
        Label fromLabel = new Label("Difficulty:");
        difficultyField = new TextField(difficulty);
        difficultyField.setPromptText("Enter Difficulty.");

        // Create the to label and field
        Label toLabel = new Label("TotalTime:");
        totalTimeField = new TextField(totalTime);
        totalTimeField.setPromptText("Enter journey TotalTime");

        Label transportTypeLabel = new Label("Rating:");
        ratingField = new TextField(rating);
        ratingField.setPromptText("Enter Rating.");

        // Create the grid pane and add the fields to it
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.addRow(0, nameLabel, nameField);
        grid.addRow(1, descriptionLabel, commentField);
        grid.addRow(2, fromLabel, difficultyField);
        grid.addRow(3, toLabel, totalTimeField);
        grid.addRow(4, transportTypeLabel, ratingField);


        // Set the content of the dialog pane to the grid pane
        getDialogPane().setContent(grid);

        // Convert the result to a tour model when the OK button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Validate input
                if (nameField.getText().isEmpty() || commentField.getText().isEmpty()
                        || difficultyField.getText().isEmpty() || totalTimeField.getText().isEmpty()
                        || ratingField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all fields.");
                    alert.showAndWait();
                } else {
                    //TODO api get
                    TourLog tourLog = TourLog.builder()
                            .logId(logId)
                            .name(nameField.getText())
                            .rating(ratingField.getText())
                            .comment(commentField.getText())
                            .difficulty(difficultyField.getText())
                            .totalTime(totalTimeField.getText())
                            .tour(Tour.builder().tourId(tourId).build())
                            .build();
                    return tourLog;
                }
            }
            return null;
        });
    }

}
