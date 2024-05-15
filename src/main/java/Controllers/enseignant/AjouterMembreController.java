package Controllers.enseignant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.esprit.entities.project.Project;
import tn.esprit.entities.project.ProjectMemberUser;
import tn.esprit.entities.project.ProjectMembers;
import tn.esprit.services.projectService.ProjectMemberUserService;
import tn.esprit.services.projectService.ProjectMembersService;
import tn.esprit.services.projectService.ProjectService;
import tn.esprit.services.userServices.UserService;

import java.io.IOException;
import java.time.LocalDateTime;

public class AjouterMembreController {
    @FXML
    private TextField mail;
    @FXML
    private Label mailmem;

    private Project project;
    private int projectId;

    @FXML
    private Label name;

    @FXML
    private Label classe;

    @FXML
    private Label matiere;

    public void setProject(Project project) {
        this.project = project;
        if (project != null) {
            System.out.println("Projet reçu : " + project.getNom());
            name.setText(project.getNom());
            classe.setText("Classe : " + project.getClasse());
            matiere.setText("Matière : " + project.getMatiere());
        } else {
            System.out.println("Le projet est null.");
        }
    }

    @FXML
    public void ajouterMembre() {
        if (project == null) {
            System.out.println("Le projet n'est pas initialisé.");
            return;
        }

        ProjectService projectService = new ProjectService();
        System.out.println("proj name" +name.getText());
        projectId = projectService.recupererIdParNom(name.getText());
        if (projectId == 0) {
            System.out.println("Le projet n'existe pas dans la base de données.");
            return;
        }
        ProjectMembersService projectMembersService = new ProjectMembersService();
        String enteredEmail = mail.getText();
        if (projectMembersService.isStudentByEmail(enteredEmail) && projectMembersService.isExist(enteredEmail)) {
            System.out.println("L'adresse e-mail saisie existe bien.");
            int idStudent = projectMembersService.getIdStudent(enteredEmail);
            ProjectMembers projectMembers = new ProjectMembers();
            projectMembers.setProjectId(projectId);
            LocalDateTime maintenant = LocalDateTime.now();
            projectMembers.setJoinedAt(java.sql.Timestamp.valueOf(maintenant));
            projectMembersService.ajouterMembre(projectMembers);
            // Récupérer l'ID du membre ajouté
            int idMember = projectMembersService.getLAstId(projectId);
            ProjectMemberUser projectMemberUser = new ProjectMemberUser();
            projectMemberUser.setIdUser(idStudent);
            projectMemberUser.setIdMember(idMember);
            System.out.println(idStudent +"hhhhh"+idMember+"hhhhhhhh"+projectId);
            ProjectMemberUserService projectMemberUserService = new ProjectMemberUserService();
            projectMemberUserService.ajouterMemberUser(projectMemberUser);
            confirimation(mail.getText()); // Pass entered email to confirmation dialog
        } else {
            System.out.println("L'adresse e-mail saisie ne correspond pas à un étudiant.");
        }

    }

        private void confirimation (String enteredEmail){
            ProjectMembersService projectMembersService = new ProjectMembersService();
            if (projectMembersService.isStudentByEmail(enteredEmail) && projectMembersService.isExist(enteredEmail)) {
                mailmem.setText(mailmem.getText() + "\n" + enteredEmail);
            }

            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Voulez-vous ajouter un autre membre ?");
            confirmationDialog.setContentText("Cliquez sur OK pour ajouter un autre membre ou Annuler pour terminer.");

            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    mail.clear();
                } else {
                    mail.clear();
                }
            });
        }

    public void retour(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/enseignant/AfficherProjet.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
