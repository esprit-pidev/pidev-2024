package Controllers.StageControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import tn.esprit.services.stageServices.CandidatureService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javafx.stage.FileChooser;

public class AfficherCandidatureController {
    private final CandidatureService SC = new CandidatureService();
    private final FileChooser fileChooser = new FileChooser();
    private ObservableList<Candidature> candidatureData = FXCollections.observableArrayList();

    @FXML
    private ListView<Candidature> candidatureListView;

    @FXML
    private TextField competences;

    @FXML
    private TextField cv;
    @FXML
    public void initialize() {
        List<Candidature> candidatures = SC.afficher();
        candidatureData.clear();
        if (candidatures.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune candidature");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y a aucune candidature à afficher.");
            alert.showAndWait();
        } else {
            candidatureData.addAll(candidatures);
            candidatureListView.setItems(candidatureData);

            if (candidatures.get(0) != null) {
                afficherDetails(candidatures.get(0));
            }
        }refresh();
    }
    private void afficherDetails(Candidature candidature) {
        competences.setText("Compétences : " + candidature.getCompetences());
        cv.setText("CV : " + candidature.getCv());
    }
    @FXML
    void fillforum(MouseEvent event) {
        Candidature candidature = candidatureListView.getSelectionModel().getSelectedItem();
        if (candidature != null) {
            competences.setText(candidature.getCompetences());
            cv.setText(candidature.getCv());
        }
    }
    public void uploadPdf(javafx.event.ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            // Define the destination directory
            String destinationDirectory = "C:\\xampp\\htdocs\\pdf";
            // Get the name of the selected file
            String originalFileName = file.getName();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String safeFileName = originalFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
            String randomFileName = System.currentTimeMillis() + "-" + UUID.randomUUID().toString() + fileExtension;

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
    void modifier(ActionEvent event) {
        Candidature selectedCandidature = candidatureListView.getSelectionModel().getSelectedItem();
        if (selectedCandidature != null) {
            String newCompetences = competences.getText();
            String newCv = cv.getText();
            if (newCompetences.isEmpty() || newCv.isEmpty()) {
                showAlert("Erreur de modification", "Les compétences et le CV sont obligatoires.");
                return;
            }
            selectedCandidature.setCompetences(newCompetences);
            selectedCandidature.setCv(newCv);
            SC.modifier(selectedCandidature);
            showAlert("Modification réussie", "La candidature a été modifiée avec succès.");
        } else {
            showAlert("Erreur de modification", "Aucune candidature sélectionnée.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void refresh() {
        List<Candidature> candidatures = SC.afficher();
        candidatureData.clear();
        candidatureData.addAll(candidatures);
        candidatureListView.setItems(candidatureData);
        if (!candidatures.isEmpty()) {
            afficherDetails(candidatures.get(0));
        }
    }
    @FXML
    void naviguezVersAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCandidature.fxml"));
            Parent root = loader.load();

            // Afficher le stage pour AjouterCandidatureController
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer le stage actuel
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
