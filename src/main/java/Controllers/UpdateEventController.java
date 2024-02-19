package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.events.Events;
import tn.esprit.services.eventsServices.EventService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class UpdateEventController implements Initializable {

    @FXML
    private Button updateEvent;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea description;

    @FXML
    private TextField name;

    @FXML
    private Button uploadImg;
    FileChooser fileChooser = new FileChooser();
    private EventService eventService = new EventService();
    private String imgName ;
    private Events eventToBeUpdated;

    public void setEventToBeUpdated(Events eventToBeUpdated) {
        this.eventToBeUpdated = eventToBeUpdated;
    }

    @FXML
    void uploadImg(ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            // Define the destination directory
            String destinationDirectory = "C:\\xampp\\htdocs\\img";
            // Get the name of the selected file
            String fileName = file.getName();
            // Create a Path for the destination file

            Path destinationPath = new File(destinationDirectory, fileName).toPath();
            try {
                // Copy the selected file to the destination directory
                Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully to: " + destinationPath);
                imgName = fileName;
                uploadImg.setText("ready");
            } catch (IOException e) {
                System.out.println("Error uploading file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateEvent.setOnAction(event -> {
            java.sql.Date sqlDate = java.sql.Date.valueOf(datePicker.getValue());
            System.out.println(sqlDate);
            eventService.modifier(new Events(1,name.getText(),description.getText(),sqlDate,imgName),eventToBeUpdated.getEventId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));

            try {
                Parent componentRoot = loader.load();
                AjouterEventController controller = new AjouterEventController();
                ((Scene) updateEvent.getScene()).setRoot(componentRoot);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

    }

}
