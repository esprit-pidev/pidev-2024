package controllers.enseignant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.services.projectService.ProjectMembersService;

import java.io.IOException;


public class GerermembresController {

    @FXML
    private TextField mail;
    private int projectId;

    public void setProjectId(int projectId) {
        this.projectId = projectId;
        System.out.println(projectId);
    }


    private void afficherMembres() {
    }

    public void toajoutermembre(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/AjouterMembre.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void supprimermembre(MouseEvent mouseEvent) {
    }
    public void ajouterMembre(MouseEvent mouseEvent) {

    }
}
