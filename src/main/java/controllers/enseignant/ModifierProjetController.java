package controllers.enseignant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

public class ModifierProjetController {


    @FXML
    private Label nomProjetLabel;
    @FXML
    private Label classeLabel;
    private String projectName;
    private String projectClasse;

    private int projectId;

    // Méthode pour définir l'ID du projet
    public void setProjectId(int projectId) {
        this.projectId = projectId;
        System.out.println("ID du projet reçu dans ModifierinfoController : " + projectId); // Ajoutez cette ligne pour vérifier l'ID reçu
    }

    public void setProjectDetails(int projectId, String projectName, String projectClasse) {
        this.projectId = projectId; // Assurez-vous d'affecter la valeur du projectId
        this.projectName = projectName;
        this.projectClasse = projectClasse;
        nomProjetLabel.setText("Nom du projet : " + projectName);
        classeLabel.setText("Classe: " + projectClasse);

        // Assurez-vous que l'ID du projet est bien reçu ici
        System.out.println("ID du projet reçu dans ModifierProjetController : " + projectId);
    }



    @FXML
    public void modNom(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/modifierNom.fxml"));
            Parent root = loader.load();
            ModifierinfoController modifierInfoController = loader.getController();
            if (projectName != null) {
                modifierInfoController.setNom(projectName);
                // Passer l'ID du projet à la page de modification du nom
                modifierInfoController.setProjectId(projectId);
                // Récupérer la scène actuelle
                Scene currentScene = nomProjetLabel.getScene();
                // Changer la racine de la scène actuelle
                currentScene.setRoot(root);
            } else {
                System.out.println("Le nom du projet est null.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void modClasse(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/modifierClasse.fxml"));
            Parent root = loader.load();
            ModifierinfoController modifierInfoController = loader.getController();
            if (projectClasse != null) {
                modifierInfoController.setClasse(projectClasse);
                // Passer l'ID du projet à la page de modification du nom
                modifierInfoController.setProjectId(projectId);

                // Obtenir le contrôleur de la fenêtre actuelle
                Stage stage = (Stage) nomProjetLabel.getScene().getWindow();
                // Remplacer le contenu de la fenêtre actuelle par la nouvelle racine
                stage.getScene().setRoot(root);
            } else {
                System.out.println("La classe est nulle.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
