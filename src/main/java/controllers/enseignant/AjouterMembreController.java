package controllers.enseignant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.entities.project.Project;
import tn.esprit.entities.project.ProjectMembers;
import tn.esprit.services.projectService.ProjectMembersService;
import tn.esprit.services.projectService.ProjectService;
import tn.esprit.services.userServices.UserService;

import java.time.LocalDateTime;

public class AjouterMembreController {
    @FXML
    private TextField mail;

    private Project project;
    int  projectId;

    @FXML
    private Label name;

    @FXML
    private Label classe;

    @FXML
    private Label matiere;

    public void setProject(Project project) {
        this.project = project;
        System.out.println("Projet reçu : " + project.getNom() );
        name.setText("Nom du projet : " + project.getNom());
        classe.setText("Classe : " + project.getClasse());
        matiere.setText("Matière : " + project.getMatiere());
        projectId = project.getId();

    }

    @FXML
    public void ajouterMembre() {
        if (project == null) {
            System.out.println("Le projet n'est pas initialisé.");
            return;
        }

        UserService userService = new UserService();
        String enteredEmail = mail.getText();
        if (userService.isStudentByEmail(enteredEmail) && userService.isExist(enteredEmail)) {
            System.out.println("L'adresse e-mail saisie existe bien.");
            int idStudent = userService.getIdStudent(enteredEmail);
            Project project = new Project();
            projectId = project.getId();
            System.out.println(projectId);
            ProjectMembers projectMembers = new ProjectMembers();
            LocalDateTime maintenant = LocalDateTime.now();
            projectMembers.setJoinedAt(java.sql.Timestamp.valueOf(maintenant));
            projectMembers.setProjectId(projectId);
            ProjectMembersService projectMembersService = new ProjectMembersService();
            projectMembersService.ajouterMembre(projectMembers);
        } else {
            System.out.println("L'adresse e-mail saisie ne correspond pas à un étudiant.");
        }
    }

}
