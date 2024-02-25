package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.project.Project;
import tn.esprit.services.projectService.ProjectService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;


public class ContainerProjectController {

    @FXML
    private Label nomLabel;
    @FXML
    private Label nom;

    @FXML
    private Label classeLabel;

    @FXML
    private Label updatedAtLabel;

    private Project project;
    @SuppressWarnings("unused")
    @FXML
    private VBox containerVBox;
    @SuppressWarnings("unused")
    @FXML
    private ImageView iconeImageView;
    private int id;


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
            ProjectService ps = new ProjectService();
            ps.supprimer(project);
            containerVBox.getChildren().remove(this);
        } else {
            System.out.println("Aucun projet associé à ce conteneur.");
        }

    }

    @FXML
    public void onEditClicked(MouseEvent mouseEvent) {
        try {
            if (project != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierProjet.fxml"));
                Parent root = loader.load();
                ModifierProjetController controller = loader.getController();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.out.println("Aucun projet associé à ce conteneur.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}






