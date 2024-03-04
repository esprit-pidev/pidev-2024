package controllers.etudiant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import tn.esprit.entities.User.User;
import tn.esprit.services.projectService.ProjectService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectsBarController implements Initializable {
    @FXML
    private VBox contmatiere;
    private String projectName;
    private int projectId;
    private int idMember;
    int IdUser = 2;
    private final ProjectService projectService = new ProjectService();

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = new User();
        user.setId(IdUser);
        List<String> userProjects = projectService.getNameProjectsByUserId(IdUser);
        afficherProjet(userProjects);
    }

    private void afficherProjet(List<String> userProjects) {
        for (String projectNames : userProjects) {
            System.out.println("Données du projet : " + projectNames);
            String projectName = (String) projectNames;
            int idProject = projectService.getIdProjectByName(projectName);
            int idMember = projectService.getIdMemberByIdUser(IdUser, idProject);
            if (projectName != null) {
                Node container;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/etudiant/containerProjectet.fxml"));
                    container = loader.load();
                    ContainerProjectetController controller = loader.getController();
                    controller.setProjectName(projectName);
                    controller.setIdMember(idMember);
                    controller.setProjectId(idProject);
                    contmatiere.getChildren().add(container);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Le nom du projet ou l'ID du projet est null");
            }
        }
    }


}
