package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.services.userServices.UserService;

import java.time.LocalDate;

public class EnseignantEditFormController {

    private final UserService userService = new UserService();

    private Enseignant enseignant;

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    @FXML
    private TextField cinTF;

    @FXML
    private DatePicker dateNaissanceTF;

    @FXML
    private TextField emailTF;

    @FXML
    private ComboBox<String> genreCB;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    public void setCinTF(String cinTF) {
        this.cinTF.setText(cinTF);
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
    void SaveTeacher(ActionEvent event) {
        enseignant.setNom(nomTF.getText());
        enseignant.setPrenom(prenomTF.getText());
        enseignant.setCin(cinTF.getText());
        enseignant.setGenre(genreCB.getValue());
        enseignant.setEmail(emailTF.getText());
        enseignant.setDate_naissance(dateNaissanceTF.getValue());
        userService.adminUpdateEnseignant(enseignant);
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) nomTF.getScene().getWindow();
        stage.close();
    }

}
