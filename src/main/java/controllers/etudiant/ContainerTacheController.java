package controllers.etudiant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tn.esprit.entities.project.Taches;
import tn.esprit.services.projectService.TacheService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ContainerTacheController {

    @FXML
    private HBox tacheDetail;

    private int projectId;
    private String projectName;

    private int idMember;

    public void loadTasksForProject(int projectId, int idMember) throws IOException {
        TacheService tacheService = new TacheService();
        List<Taches> taches = tacheService.getTacheByIdAndUserIdAndProjectId(idMember, projectId);

        for (Taches tache : taches) {
            Node container;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/etudiant/tache.fxml"));
            container = loader.load();
            TacheController tacheController = loader.getController();
            int idProjectMember = tache.getIdProjectMembers();
            String description = tache.getDescription();
            String etat = tache.getEtat();
            Date dateAjout = tache.getDate_ajout();
            Date deadline = tache.getDedline();
            String taskDetails = "ID du membre du projet: " + idProjectMember + ", Description: " + description + ", État: " + etat + "\nDate d'ajout: " + dateAjout + ", Deadline: " + deadline;
            tacheController.setTache(taskDetails);
            tacheDetail.getChildren().add(container);
            System.out.println("**Tache:** " + taskDetails);
            if (tacheDetail.getChildren().contains(container)) {
                System.out.println("**TacheDetail ajouté à containertache**");
            } else {
                System.out.println("**Échec de l'ajout de tacheDetail à containertache**");
            }
        }
    }
}
