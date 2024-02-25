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
import javafx.stage.Stage;
import tn.esprit.entities.stage.Candidature;
import tn.esprit.services.stageServices.CandidatureService;

import java.io.IOException;
import java.util.List;

public class AfficherCandidatureController {

    private final CandidatureService candidatureService = new CandidatureService();
    private ObservableList<Candidature> candidatureData = FXCollections.observableArrayList();

    @FXML
    private ListView<Candidature> candidatureListView;

    @FXML
    private Label competences;

    @FXML
    private Label cv;

    @FXML
    public void initialize() {
        List<Candidature> candidatures = candidatureService.afficher(); // Récupérer la liste des candidatures à partir du service

        if (candidatures.isEmpty()) {
            // Afficher un message si la liste est vide
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune candidature");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y a aucune candidature à afficher.");
            alert.showAndWait();
        } else {
            // Afficher les candidatures dans la ListView
            candidatureData.addAll(candidatures);
            candidatureListView.setItems(candidatureData);

            // Définir un gestionnaire d'événements pour afficher les détails de la candidature sélectionnée
            candidatureListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                afficherDetails(newValue);
            });

            // Afficher les détails de la première candidature dans la liste
            afficherDetails(candidatures.get(0));
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

    private void afficherDetails(Candidature candidature) {
        competences.setText("Compétences : " + candidature.getCompetences());
        cv.setText("CV : " + candidature.getCv());
    }
}
