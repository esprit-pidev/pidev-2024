package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.User.ResponsableClub;
import tn.esprit.services.userServices.UserService;

import java.io.IOException;
import java.time.LocalDate;

public class EtudiantEditFormController {

    private AdminController adminController;

    public void setAdminController(AdminController controller) {
        this.adminController=controller;
    }

    private final UserService userService = new UserService();

    private Etudiant etudiant = null;

    private ResponsableClub responsableClub = null;

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public void setResponsableClub(ResponsableClub responsableClub) {
        this.responsableClub = responsableClub;
    }


    @FXML
    private TextField cinTF;

    @FXML
    private TextField classeTF;

    @FXML
    private DatePicker dateNaissanceTF;

    @FXML
    private TextField emailTF;

    @FXML
    private ComboBox<String> genreCB;

    @FXML
    private TextField niveauTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;


    public void setCinTF(String cinTF) {
        this.cinTF.setText(cinTF);
    }

    public void setClasseTF(String classeTF) {
        this.classeTF.setText(classeTF);
    }

    public void setDateNaissanceTF(LocalDate dateNaissanceTF) {
        this.dateNaissanceTF.setValue(dateNaissanceTF);
    }

    public void setEmailTF(String emailTF) {
        this.emailTF.setText(emailTF);
    }

    public void setGenreCB(String genreCB) {
        this.genreCB.setValue(genreCB);
    }

    public void setNiveauTF(int niveauTF) {
        this.niveauTF.setText(String.valueOf(niveauTF));
    }

    public void setNomTF(String nomTF) {
        this.nomTF.setText(nomTF);
    }

    public void setPrenomTF(String prenomTF) {
        this.prenomTF.setText(prenomTF);
    }


    @FXML
    public void initialize() {
        genreCB.getItems().addAll("Male", "Female");
        genreCB.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        });

    }

    @FXML
    void SaveStudent(ActionEvent event) throws IOException {
        if (responsableClub==null) {
            etudiant.setNom(nomTF.getText());
            etudiant.setPrenom(prenomTF.getText());
            etudiant.setCin(cinTF.getText());
            etudiant.setNiveau(Integer.parseInt(niveauTF.getText()));
            etudiant.setClasse(classeTF.getText());
            etudiant.setGenre(genreCB.getValue());
            etudiant.setEmail(emailTF.getText());
            etudiant.setDate_naissance(dateNaissanceTF.getValue());
            userService.adminUpdateEtudiant(etudiant);
            adminController.reloadLoginScene(1);
        }
        if (etudiant==null) {
            responsableClub.setNom(nomTF.getText());
            responsableClub.setPrenom(prenomTF.getText());
            responsableClub.setCin(cinTF.getText());
            responsableClub.setNiveau(Integer.parseInt(niveauTF.getText()));
            responsableClub.setClasse(classeTF.getText());
            responsableClub.setGenre(genreCB.getValue());
            responsableClub.setEmail(emailTF.getText());
            responsableClub.setDate_naissance(dateNaissanceTF.getValue());
            userService.adminUpdateEtudiant(responsableClub);
            adminController.reloadLoginScene(2);
        }
        closeStage();

    }

    private void closeStage() {
        Stage stage = (Stage) nomTF.getScene().getWindow();
        stage.close();
    }

}
