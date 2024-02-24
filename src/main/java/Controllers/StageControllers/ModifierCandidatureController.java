package Controllers.StageControllers;

import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import tn.esprit.services.stageServices.CandidatureService;

public class ModifierCandidatureController {

    @FXML
    private TextField competences;

    @FXML
    private TextField cv;
    @FXML
    private TextField id;

    private Candidature C =new Candidature();
    private final CandidatureService SC =new CandidatureService();

    public void setCandidature(Candidature C) {
        SC.setCandidature(C.getOffre_Id());
    }

    @FXML
    void modifier(ActionEvent event) {
        Candidature C = SC.getById(16);
        C.setCompetences(competences.getText());
        C.setCv(cv.getText());
        SC.modifier(C);
        showAlert("Modification réussie", "La candidature a été modifiée avec succès.");
    }

    @FXML
    void supprimer(ActionEvent event) {
        Candidature C = SC.getById(16);
        C.setId(Integer.parseInt(id.getText()));
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer cette candidature ?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            SC.supprimer(C.getOffre_Id());
            showAlert("Suppression réussie", "La candidature a été supprimée avec succès.");
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
