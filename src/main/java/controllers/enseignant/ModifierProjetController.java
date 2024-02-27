package controllers.enseignant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
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
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.out.println("La classe est nulle.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void gererMembres(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/gerermembres.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
