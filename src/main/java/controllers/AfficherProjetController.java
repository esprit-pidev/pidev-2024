package controllers;

import controllers.ContainerProjectController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.project.Project;
import tn.esprit.services.projectService.ProjectService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherProjetController implements Initializable {
    @FXML
    private ComboBox<String> comboBoxClasses;

    @FXML
    private VBox containerVBox;

    ProjectService ps = new ProjectService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Récupérer la liste des classes et les ajouter à la ComboBox
        List<String> classes = ps.displayClasses();
        comboBoxClasses.getItems().add("Toutes les classes"); // Ajouter l'option "Toutes les classes"
        comboBoxClasses.getItems().addAll(classes);

        // Ajouter un écouteur d'événements à la ComboBox
        comboBoxClasses.setOnAction(event -> {
            String selectedClass = comboBoxClasses.getValue();
            if (selectedClass != null) {
                if (selectedClass.equals("Toutes les classes")) {
                    afficherTousLesProjets();
                } else {
                    afficherProjetsParClasse(selectedClass);
                }
            }
        });

        // Afficher tous les projets par défaut
        afficherTousLesProjets();
    }

    private void afficherTousLesProjets() {
        // Effacer tous les éléments du container
        containerVBox.getChildren().clear();

        // Récupérer tous les projets et les afficher
        List<Project> projects = ps.displayProjectsInfo();
        afficherProjets(projects);
    }



    private void afficherProjetsParClasse(String classe) {
        // Effacer tous les éléments du container
        containerVBox.getChildren().clear();

        // Récupérer les projets par classe et les afficher
        List<Project> projects = ps.getProjectsByClasse(classe);
        afficherProjets(projects);
    }

    private void afficherProjets(List<Project> projects) {
        // Ajouter chaque projet au container
        for (Project project : projects) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ContainersProjet.fxml"));
            Node container;
            try {
                container = loader.load();
                ContainerProjectController controller = loader.getController();
                controller.setProject(project);
                // Passer l'ID du projet au contrôleur de la page de modification
                containerVBox.getChildren().add(container);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onAdd(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProjet.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

