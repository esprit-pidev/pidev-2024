package Controllers.StageControllers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.stage.Candidature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import tn.esprit.services.stageServices.CandidatureService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ModifierCandidatureController {

    @FXML
    private TextField competences;
    @FXML
    private TextField cv;
    @FXML
    private TextField id;
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
    private Candidature C = new Candidature();
    private final CandidatureService SC = new CandidatureService();

    public void setCandidature(Candidature C) {

    }
    @FXML
    void modifier(ActionEvent event) {
        try {
            int candidatureId = Integer.valueOf(id.getText());
            Candidature C = SC.getById(candidatureId);
            if (C != null && "en cours".equals(C.getStatus())) {
                C.setCompetences(competences.getText());
                C.setCv(cv.getText());
                SC.modifier(C);
                showAlert("Modification réussie", "La candidature a été modifiée avec succès.");
            } else if (C == null) {
                showAlert("Erreur de modification", "La candidature n'existe pas.");
            } else {
                showAlert("Erreur de modification", "Le statut n'est pas 'en cours'.");
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez saisir un ID valide pour la candidature.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
