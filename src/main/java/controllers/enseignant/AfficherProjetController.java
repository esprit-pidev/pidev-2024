package controllers.enseignant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    private Pane containerVBox;


    ProjectService ps = new ProjectService();
    Project p = new Project();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Récupérer la liste des classes et les ajouter à la ComboBox
        List<String> classes = ps.displayClasses();
        comboBoxClasses.getItems().add("Toutes les classes");
        comboBoxClasses.getItems().addAll(classes);
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
        afficherTousLesProjets();
    }

    private void handleClickOnProject(int projectId) {
        p.setId(projectId) ;
        System.out.println("ID du projet sélectionné : " + projectId);
    }

    private void afficherTousLesProjets() {
        containerVBox.getChildren().clear();
        List<Project> projects = ps.displayProjectsInfo();
        afficherProjets(projects);
    }

    private void afficherProjetsParClasse(String classe) {
        containerVBox.getChildren().clear();
        List<Project> projects = ps.getProjectsByClasse(classe);
        afficherProjets(projects);
    }

    private void afficherProjets(List<Project> projects) {
        for (Project project : projects) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/ContainersProjet.fxml"));
            Node container;
            try {
                container = loader.load();
                ContainerProjectController controller = loader.getController();
                controller.setProject(project);
                container.setOnMouseClicked(event -> handleClickOnProject(project.getId()));
                containerVBox.getChildren().add(container);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
    @FXML
    public void onAdd(MouseEvent mouseEvent) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/AjouterProjet.fxml"));
                Parent root = loader.load();
                Scene scene = containerVBox.getScene();
                scene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    }


