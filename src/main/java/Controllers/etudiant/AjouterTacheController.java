package Controllers.etudiant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.esprit.entities.project.Taches;
import tn.esprit.services.projectService.TacheService;

import java.io.IOException;
import java.time.LocalDate;

public class AjouterTacheController {

    @FXML
    private TextField desc;
    private int idMember;
    @FXML
    private DatePicker deadline = new DatePicker();
    private int projetId;

    public void setDet(int idMember , int projetId) {
        this.idMember = idMember;
        this.projetId = projetId;
        System.out.println(idMember +"------------" );
    }

    @FXML
    public void ajouterTache(MouseEvent event) {
        if (idMember == 0) {
            System.err.println("idMember n'est pas initialisé correctement.");
            return;
        }
        String description = desc.getText();
        java.util.Date deadlineDate = null;

        if (deadline.getValue() == null || deadline.getValue().isBefore(LocalDate.now())) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("La date limite doit être postérieure ou égale à la date d'aujourd'hui.");
            alert.showAndWait();
            return;
        }else {
            deadlineDate = java.sql.Date.valueOf(deadline.getValue());
        }

        Taches nouvelleTache = new Taches();
        nouvelleTache.setDescription(description);
        nouvelleTache.setDedline(deadlineDate);
        nouvelleTache.setIdProjectMembers(idMember);
        java.sql.Date dateAjout = java.sql.Date.valueOf(LocalDate.now());
        nouvelleTache.setDate_ajout(dateAjout);
        nouvelleTache.setEtat("à faire");
        TacheService tacheService = new TacheService();
        tacheService.addTache(nouvelleTache);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/etudiant/HomePage.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
