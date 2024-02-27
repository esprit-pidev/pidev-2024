package controllers.enseignant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.entities.project.Project;
import tn.esprit.services.projectService.ProjectService;

public class ModifierinfoController {
  @FXML
  private Label nompr;

  @FXML
  private Label classepr;

  @FXML
  private TextField nouveaunom;
  @FXML
  private TextField nouveauclasse;

  private final ProjectService projectService;
  private int projectId;

  public ModifierinfoController() {
    this.projectService = new ProjectService();
  }

  public void setNom(String projectName) {
    nompr.setText(projectName);
  }

  public void setClasse(String projectClasse) {
    classepr.setText(projectClasse);
  }



  public void setProjectId(int projectId) {
    this.projectId = projectId;
    System.out.println("ID du projet reçu : " + projectId);
  }

  public void SaveNameButtonAction(ActionEvent actionEvent) {
    String nouveauNom = nouveaunom.getText();
    if (!nouveauNom.isEmpty()) {
      Project project = new Project();
      project.setId(projectId);
      project.setNom(nouveauNom);
      projectService.modifierNomProjet(project);
      System.out.println("Nom du projet mis à jour avec succès !");
    } else {
      System.out.println("Veuillez entrer un nouveau nom pour le projet.");
    }
  }

  public void SaveClasseButtonAction(ActionEvent actionEvent) {
    String nouvelleClasse = nouveauclasse.getText();
    if (!nouvelleClasse.isEmpty()) {
      Project project = new Project();
      project.setId(projectId);
      project.setClasse(nouvelleClasse);
      projectService.modifierClasseProjet(project);
      System.out.println("Classe du projet mise à jour avec succès !");
    } else {
      System.out.println("Veuillez entrer une nouvelle classe pour le projet.");
    }
  }


}
