package Controllers.StageControllers;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import tn.esprit.services.stageServices.CandidatureService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

public class AjouterCandidatureController {
    private Offre offre;
    private final UserService us = new UserService();

    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();

    Etudiant etudiant = (Etudiant) us.getById(userLoggedIn.getId());

    public void initData(Offre offre) {
        this.offre = offre;
    }
    private final CandidatureService SC =new CandidatureService();

    @FXML
    private TextField competences;

    @FXML
    private TextField cv;
    private final FileChooser fileChooser = new FileChooser();

    public void uploadPdf(javafx.event.ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            // Get the name of the selected file
            String originalFileName = file.getName();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String safeFileName = originalFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
            String randomFileName = System.currentTimeMillis() + "-" + UUID.randomUUID().toString() + fileExtension;

            // Define the destination directory
            String destinationDirectory = "C:\\xampp\\htdocs\\pdf";
            // Create a Path for the destination file
            Path destinationPath = new File(destinationDirectory, randomFileName).toPath();
            try {
                // Copy the selected file to the destination directory
                Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully to: " + destinationPath);
                // Update the TextField with the file path
                cv.setText(destinationPath.toString());
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

            AfficherCandidatureController controller = loader.getController();

            Scene scene = new Scene(root);
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
            return;
        }
        // Récupérer la date actuelle
        Date currentDate = new Date();
        try {
            SC.ajouter(offre.getId(), etudiant, new java.sql.Date(currentDate.getTime()), "En attente", competences.getText(), cv.getText());
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
