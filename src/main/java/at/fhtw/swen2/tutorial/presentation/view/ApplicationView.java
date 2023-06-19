package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.ApplicationContextProvider;
import at.fhtw.swen2.tutorial.presentation.StageAware;
import at.fhtw.swen2.tutorial.presentation.TourListChangePublisher;
import at.fhtw.swen2.tutorial.presentation.events.ApplicationShutdownEvent;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogViewModel;
import at.fhtw.swen2.tutorial.service.MapQuestService;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.impl.TourLogServiceImpl;
import at.fhtw.swen2.tutorial.service.impl.TourServiceImpl;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
@Slf4j
public class ApplicationView implements Initializable, StageAware {

    ApplicationEventPublisher publisher;
    @Autowired
    TourServiceImpl tourService;
    @Autowired
    TourLogServiceImpl tourLogService;

    @FXML
    private ListView<Tour> listView;

    @FXML
    BorderPane layout;

    @FXML
    MenuItem miAbout;

    // Toolbar, at some point break out
    @FXML
    Label tbMonitorStatus;
    Circle monitorStatusIcon = new Circle(8);
    @Autowired
    TourListViewModel tourListViewModel;
    @Autowired
    TourLogViewModel tourLogViewModel;
    SimpleObjectProperty<Stage> stage = new SimpleObjectProperty<>();
    @Autowired
    private MapQuestService mapQuestService;

    public ApplicationView(ApplicationEventPublisher publisher) {
        log.debug("Initializing application controller");
        this.publisher = publisher;
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        this.mapQuestService = ApplicationContextProvider.getApplicationContext().getBean(MapQuestService.class);
        stage.addListener((obv, o, n) -> n.setTitle(rb.getString("app.title")));
        tbMonitorStatus.setGraphic(monitorStatusIcon);
    }

    @FXML
    public void onFileClose(ActionEvent event) {
        publisher.publishEvent(new ApplicationShutdownEvent(event.getSource()));
    }

    @FXML
    public void onHelpAbout(ActionEvent event) {
        new AboutDialogView().show();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage.setValue(stage);
    }

    @FXML
    public void handleExportCurrentPDF(ActionEvent event) {
        try {
            Tour tour = tourListViewModel.getSelectedTour();
            if (tour == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Please choose a tour");
                return;
            }
            List<TourLog> logs = tourLogViewModel.getTourLogItems();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(tour.getName() + ".pdf"));
            document.open();
            document.add(new Paragraph(tour.getName()));
            document.add(new Paragraph("Tour Name: " + tour.getName()));
            document.add(new Paragraph("Tour Description: " + tour.getDescription()));
            document.add(new Paragraph("From: " + tour.getFrom()));
            document.add(new Paragraph("Tour To: " + tour.getTo()));
            document.add(new Paragraph("Tour TransportType: " + tour.getTransportType()));
            document.add(new Paragraph("Tour EstimatedTime: " + tour.getEstimatedTime()));
            document.add(new Paragraph("Tour Distance: " + tour.getTourDistance()));
            document.add(new Paragraph("Tour RouteInformation: " + tour.getRouteInformation()));
//            String url = tour.getRouteInformation();
//            if (url != null && url.startsWith("http")) {
//                Image image = mapQuestService.getStaticMapImage(url);
//                Paragraph paragraph = new Paragraph();
//                paragraph.add(image);
//                document.add(paragraph);
//            }

            for (TourLog tourLog : tourLogViewModel.getTourLogItems()) {
                document.add(new Paragraph("Tour Log Name: " + tourLog.getName()));
                document.add(new Paragraph("Tour Log Comment: " + tourLog.getComment()));
                document.add(new Paragraph("Tour Log Difficulty: " + tourLog.getDifficulty()));
                document.add(new Paragraph("Tour Log Rating: " + tourLog.getRating()));
                document.add(new Paragraph("Tour Log TotalTime: " + tourLog.getTotalTime()));
            }

            document.close();

            File file = new File(tour.getName() + ".pdf");
            file.createNewFile();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Export PDF Success.");


        } catch (IOException | DocumentException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Export PDF Fail.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExportSummaryPDF(ActionEvent event) {
        try {
            List<Tour> tours = tourListViewModel.getTourListItems();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Summary" + ".pdf"));
            document.open();

            document.add(new Paragraph("Tour Number: " + tours.size()));
            document.add(new Paragraph("\n"));

            tours.forEach(tour -> {
                try {
                    document.add(new Paragraph(tour.getName()));
                    document.add(new Paragraph("Tour Description: " + tour.getDescription()));
                    document.add(new Paragraph("From: " + tour.getFrom()));
                    document.add(new Paragraph("Tour To: " + tour.getTo()));
                    document.add(new Paragraph("Tour TransportType: " + tour.getTransportType()));
                    document.add(new Paragraph("Tour EstimatedTime: " + tour.getEstimatedTime()));
                    document.add(new Paragraph("Tour TourDistance: " + tour.getTourDistance()));
                    document.add(new Paragraph("\n"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            document.close();

            File file = new File("Summary.pdf");
            file.createNewFile();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Export PDF Success.");
        } catch (IOException | DocumentException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Export PDF Fail.");
            e.printStackTrace();
        }
    }

    public void importPDF(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Choose File");
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if(file == null){
            return ;
        }
        String filePath = file.getAbsolutePath();
        log.info("choose file path: " + filePath);

        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        String[] lines = text.split("\\r?\\n");

        Tour.TourBuilder tourBuilder = Tour.builder();
        List<TourLog> logs = new ArrayList<>();
        int index = -1;

        for (String line : lines) {
            if (line.startsWith("Tour Name: ")) {
                tourBuilder.name(line.substring("Tour Name: ".length()));
            } else if (line.startsWith("Tour Description: ")) {
                tourBuilder.description(line.substring("Tour Description: ".length()));
            } else if (line.startsWith("From: ")) {
                tourBuilder.from(line.substring("From: ".length()));
            } else if (line.startsWith("Tour To: ")) {
                tourBuilder.to(line.substring("To: ".length()));
            } else if (line.startsWith("Tour TransportType: ")) {
                tourBuilder.transportType(line.substring("Transport Type: ".length()));
            } else if (line.startsWith("Tour EstimatedTime: ")) {
                tourBuilder.estimatedTime(line.substring("Estimated Time: ".length()));
            } else if (line.startsWith("Tour TourDistance: ")) {
                tourBuilder.tourDistance(line.substring("Tour Distance: ".length()));
            } else if (line.startsWith("Tour RouteInformation: ")) {
                tourBuilder.routeInformation(line.substring("Tour Route Information: ".length()));
            } else if (line.startsWith("Tour Log Name: ")) {
                index = index + 1;
                logs.add(TourLog.builder().build());
                logs.get(index).setName(line.substring("Tour Log Name: ".length()));
            } else if (line.startsWith("Tour Log Comment: ")) {
                logs.get(index).setComment(line.substring("Tour Log Comment: ".length()));
            } else if (line.startsWith("Tour Log Difficulty: ")) {
                logs.get(index).setDifficulty(line.substring("Tour Log Difficulty: ".length()));
            } else if (line.startsWith("Tour Log Rating: ")) {
                logs.get(index).setRating(line.substring("Tour Log Rating: ".length()));
            } else if (line.startsWith("Tour Log Total Time: ")) {
                logs.get(index).setTotalTime(line.substring("Tour Log Total Time: ".length()));
            }
        }
        tourService.addNew(tourBuilder.build());
        TourListChangePublisher.getInstance().notifyChange();
        logs.forEach(tourLog -> {
            log.info("import Log: "+tourLog.toString());
            tourLogService.addNew(tourBuilder.build(), tourLog);
        });
    }


}
