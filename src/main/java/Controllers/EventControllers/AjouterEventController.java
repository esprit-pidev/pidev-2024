package Controllers.EventControllers;

import tn.esprit.entities.events.Events;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.services.eventsServices.EventService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterEventController implements Initializable {
    @FXML
    private Button addEvent;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea description;

    @FXML
    private TextField name;
    @FXML
    private VBox eventsContainer;
    @FXML
    private Button uploadImg;
    private String imgName ;
    FileChooser fileChooser = new FileChooser();
    private EventService eventService = new EventService();


    public void uploadImg(javafx.event.ActionEvent event) {
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
    public void displayEvents(){
        List<Events> events = eventService.display();
        eventsContainer.getChildren().clear();
        for (Events eventt : events){
            Text eventName = new Text(eventt.getEventName());
            eventName.setWrappingWidth(150);
            Button delete = new Button("Delete");
            Button update = new Button("Update");
            delete.setPrefHeight(20);
            update.setPrefHeight(20);
            HBox container1 =new HBox();
            HBox container2 =new HBox();
            container1.setSpacing(100);
            container2.setSpacing(10);
            container2.getChildren().addAll(delete,update);
            container1.getChildren().addAll(eventName,container2);
            delete.setOnAction(e->{
                deleteEvent(e,eventt);

            });

            update.setOnAction(event ->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvent.fxml"));
                Parent root;
                try {
                    root = loader.load();
                    UpdateEventController controller;
                    controller = loader.getController();
                    controller.setEventToBeUpdated(eventt);
                    ((Scene) update.getScene()).setRoot(root);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });
            eventsContainer.getChildren().add(container1);
            eventsContainer.setPadding(new Insets(5,0,0,5));
        }
    }

    private void deleteEvent(ActionEvent e, Events eventt) {
        eventService.supprimer(eventt);
        displayEvents();
    }

    public void addEventt(ActionEvent event) {
        java.sql.Date sqlDate = java.sql.Date.valueOf(datePicker.getValue());
        System.out.println(sqlDate);
        System.out.println("worked");
        eventService.ajouter(new Events(1,name.getText(),description.getText(),sqlDate,imgName));
        displayEvents();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEvent.setOnAction(this::addEventt);
        eventsContainer.setSpacing(10);
        displayEvents();

    }



}
