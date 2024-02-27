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
        List<Candidature> candidatures = candidatureService.afficher();
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

            candidatureListView.setOnMouseClicked(event -> {
                Candidature selectedCandidature = candidatureListView.getSelectionModel().getSelectedItem();
                if (selectedCandidature != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCv.fxml"));
                        Parent root = loader.load();

                        AfficherCvController controller = loader.getController();
                        controller.loadDetails(selectedCandidature.getCv(), selectedCandidature.getCompetences());

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            if (candidatures.get(0) != null) {
                afficherDetails(candidatures.get(0));
            }
        }
    }

    private void afficherDetails(Candidature candidature) {
        competences.setText("Compétences : " + candidature.getCompetences());
        cv.setText("CV : " + candidature.getCv());
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
}
