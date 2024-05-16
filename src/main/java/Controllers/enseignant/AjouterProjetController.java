package Controllers.enseignant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.project.Project;
import tn.esprit.services.projectService.ProjectService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;
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
    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn();
    private final UserService us = new UserService();



    @FXML
    void ajouterProjet() {
        String nomProjet = nom.getText();
        String descriptionProjet = description.getText();
        String classeProjet = classe.getText();
        String matiereProjet = matiere.getText();
        ProjectService projectService = new ProjectService();

        if (nomProjet.isEmpty() || descriptionProjet.isEmpty() || classeProjet.isEmpty() || matiereProjet.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
        } else {
            if (!projectService.matiereExists(matiereProjet)) {
                showAlert("La matière spécifiée n'existe pas.");
            } else if (projectService.projetExists(nomProjet)) {
                showAlert("Un projet avec ce nom existe déjà.");
            } else {
                Enseignant enseignant = (Enseignant) us.getById(userLoggedIn.getId());
                Project nouveauProjet = new Project();
                nouveauProjet.setUser(enseignant);
                nouveauProjet.setNom(nom.getText());
                nouveauProjet.setDescription(description.getText());
                nouveauProjet.setClasse(classe.getText());
                nouveauProjet.setMatiere(matiere.getText());
                nouveauProjet.setCreated_at(new Date());
                nouveauProjet.setUpdated_at(new Date());
                // Appel de la méthode ajouter() pour insérer le nouveau projet et récupérer l'objet Project avec l'ID attribué
                projectService.ajouter(nouveauProjet);
                int nouveauProjetId = nouveauProjet.getId(); // Récupération de l'ID du nouveau projet ajouté
                // Après l'insertion réussie, naviguez vers la page AjouterMembre.fxml
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/AjouterMembre.fxml"));
                    Parent ajoutMembresParent = loader.load();
                    AjouterMembreController ajouterMembreController = loader.getController();
                    ajouterMembreController.setProject(nouveauProjet);
                    Scene ajoutMembresScene = new Scene(ajoutMembresParent);
                    Stage stage = (Stage) nom.getScene().getWindow();
                    stage.setScene(ajoutMembresScene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public void goback(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/AfficherProjet.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
