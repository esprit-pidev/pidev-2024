package controllers.enseignant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.User.User;
import tn.esprit.entities.project.Project;
import tn.esprit.services.projectService.ProjectService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class AjouterProjetController {

    @FXML
    private TextField classe;

    @FXML
    private TextField description;

    @FXML
    private TextField matiere;

    @FXML
    private TextField nom;

    private final ProjectService projectService = new ProjectService();



    @FXML
    void ajouterProjet() {
        String nomProjet = nom.getText();
        String descriptionProjet = description.getText();
        String classeProjet = classe.getText();
        String matiereProjet = matiere.getText();

        if (nomProjet.isEmpty() || descriptionProjet.isEmpty() || classeProjet.isEmpty() || matiereProjet.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
        } else {
            User user = new User();
            user.setId(1);
            Project nouveauProjet = new Project();
            nouveauProjet.setUser(user);
            nouveauProjet.setNom(nomProjet);
            nouveauProjet.setDescription(descriptionProjet);
            nouveauProjet.setClasse(classeProjet);
            nouveauProjet.setMatiere(matiereProjet);

            LocalDateTime maintenant = LocalDateTime.now();
            nouveauProjet.setCreated_at(java.sql.Timestamp.valueOf(maintenant));
            nouveauProjet.setUpdated_at(java.sql.Timestamp.valueOf(maintenant));

            projectService.ajouter(nouveauProjet);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/AfficherProjet.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) nom.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
