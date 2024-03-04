package controllers.enseignant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.project.Project;
import tn.esprit.services.projectService.ProjectService;

import java.io.IOException;
import java.text.SimpleDateFormat;


public class ContainerProjectController {

    @FXML
    private Label nomLabel;

    @FXML
    private Label classeLabel;

    @FXML
    private Label updatedAtLabel;
    @FXML
    private Pane container;

    private Project project;

    public void setProject(Project project) {
        this.project = project;
        updateLabels();
    }

    private void updateLabels() {
        if (project != null) {
            nomLabel.setText(project.getNom());
            classeLabel.setText(project.getClasse());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            updatedAtLabel.setText(dateFormat.format(project.getUpdated_at()));
        }
    }

    @FXML
    public void onDeleteClicked() {
        if (project != null) {
            int projectId = project.getId();
            System.out.println("Suppression du projet avec l'ID : " + projectId);
            ProjectService ps = new ProjectService();
            ps.supprimer(projectId);

            // Supprimer le conteneur du projet de l'interface utilisateur
            if (container != null && container.getParent() instanceof VBox) {
                ((VBox) container.getParent()).getChildren().remove(container);
            }
        } else {
            System.out.println("Aucun projet associé à ce conteneur.");
        }
    }


    @FXML
    public void onEditClicked(MouseEvent mouseEvent) {
        try {
            if (project != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/ModifierProjet.fxml"));
                Parent root = loader.load();

                // Passer l'ID du projet à la fenêtre de modification
                ModifierProjetController modifierProjetController = loader.getController();
                modifierProjetController.setProjectDetails(project.getId(), project.getNom(), project.getClasse());

                // Passer l'ID du projet à ModifierinfoController
                modifierProjetController.setProjectId(project.getId());

                // Récupérer le conteneur parent de la fenêtre actuelle
                Scene scene = nomLabel.getScene();
                // Remplacer le contenu de ce conteneur par la page ModifierProjet
                scene.setRoot(root);
            } else {
                System.out.println("Aucun projet associé à ce conteneur.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}








