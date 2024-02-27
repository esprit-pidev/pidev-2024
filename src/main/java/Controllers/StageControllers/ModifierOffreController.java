package Controllers.StageControllers;

import tn.esprit.entities.stage.Offre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.stageServices.OffreService;

import java.io.IOException;


public class ModifierOffreController {
    private final OffreService OS =new OffreService();

    @FXML
    private TextField titre;

    @FXML
    private TextField competences;

    @FXML
    private TextField description;

    @FXML
    private TextField nbr;
    @FXML
    private TextField id;

    private Offre O =new Offre();

    @FXML
    void modify(ActionEvent event) {
        Offre O = OS.getByEntreprise_id(2);
        O.setId(Integer.parseInt(id.getText()));
        O.setTitre(titre.getText());
        O.setCompetences(competences.getText());
        O.setDescription(description.getText());
        O.setNbr(Integer.parseInt(nbr.getText()));
        OS.modifier(O);
        showAlert("Modification réussie", "L'offre de stage a été modifiée avec succès.");
    }

    @FXML
    void Delete(ActionEvent event) {
        O.setId(Integer.parseInt(id.getText()));
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer cette offre de stage ?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            OS.supprimer(O.getId());
            showAlert("Suppression réussie", "L'offre de stage a été supprimée avec succès.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOffre.fxml"));
            Parent root = loader.load();

            // Passer des données à AfficherOffreController si nécessaire
            AfficherOffreController AO = loader.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
