package controllers.etudiant;

import controllers.enseignant.ModifierinfoController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class ContainerProjectetController {
    @FXML
    private Label matiere;
    private String projectName;
    private int projectId;
    private int idMember;

    public void setProjectName(String projectName) {
        this.projectName = projectName;
        updateLabels();
    }

    public void setProjectId(int projectId) {

        this.projectId = projectId;
        System.out.println(projectId);
    }
    public void setIdMember(int idMember) {
        this.idMember = idMember;
        System.out.println(idMember);
    }
    private void updateLabels() {
        matiere.setText(projectName);
        System.out.println(projectName);
    }
    public void transm(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/etudiant/containerTache.fxml"));
            Parent root = loader.load();
            ContainerTacheController controller = loader.getController();
            System.out.println("ID du projet sélectionné yaa rabii : " + projectId);
            System.out.println("Project name ya rabii :" + projectName);
            System.out.println("IdMember yaa rabiii    " + idMember);
            controller.loadTasksForProject(projectId, idMember);
        }
    }

