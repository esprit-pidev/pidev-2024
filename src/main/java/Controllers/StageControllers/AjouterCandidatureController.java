package Controllers.StageControllers;

import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.scene.control.Alert;

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
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCandidature.fxml"));
            Parent root = loader.load();

            // Passer des données à AfficherCandidatureController si nécessaire
            AfficherCandidatureController controller = loader.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void naviguezVersModifier(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ModifierCandidature.fxml"));
            Parent root1 = loader1.load();

            ModifierCandidatureController AO = loader1.getController();

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void Ajouter(ActionEvent event) {
        if (competences.getText().isEmpty() || cv.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Sortir de la méthode si un champ est vide
        }

        try {
            SC.ajouter(new Candidature(22, 2, new Date(), "en cours", competences.getText(), cv.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'ajout a été effectué avec succès.");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
