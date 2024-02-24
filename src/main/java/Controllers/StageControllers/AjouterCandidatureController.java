package Controllers.StageControllers;

import tn.esprit.entities.stage.Candidature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.stageServices.CandidatureService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser;


public class AjouterCandidatureController {
    private final CandidatureService SC =new CandidatureService();

    @FXML
    private TextField competences;

    @FXML
    private TextField cv;
    private final FileChooser fileChooser = new FileChooser();


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
                cv.setText(fileName); // Update the TextField with the file name
            } catch (IOException e) {
                System.out.println("Error uploading file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    @FXML
    void Ajouter(ActionEvent event) {
        SC.ajouter(new Candidature(18, 2, new Date(), "accepter", competences.getText(), cv.getText()));
    }



}
