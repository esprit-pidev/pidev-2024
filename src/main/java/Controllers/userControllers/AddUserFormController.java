package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.User.Entreprise;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.services.userServices.UserService;

import java.sql.SQLException;

public class AddUserFormController {

    private final UserService userService = new UserService();

    @FXML
    private TextField cinETF;

    @FXML
    private TextField cinTF;

    @FXML
    private TextField classeTF;

    @FXML
    private DatePicker dateNaissanceETF;

    @FXML
    private DatePicker dateNaissanceTF;

    @FXML
    private TextField emailETF;

    @FXML
    private TextField emailEnTF;

    @FXML
    private TextField emailTF;

    @FXML
    private Label errorMail;

    @FXML
    private Label errorMailE;

    @FXML
    private ComboBox<String> genreCB;

    @FXML
    private ComboBox<String> genreECB;

    @FXML
    private TextField localEnTF;

    @FXML
    private TextField niveauTF;

    @FXML
    private TextField nomETF;

    @FXML
    private TextField nomEnTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField paysEnTF;

    @FXML
    private TextField pdwETF;

    @FXML
    private TextField prenomETF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField pwdEnTF;

    @FXML
    private TextField pwdTF;

    @FXML
    private TextField websiteEnTF;

    @FXML
    public void initialize() {
        genreCB.getItems().addAll("Male", "Female");
        genreCB.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        });

        genreECB.getItems().addAll("Male", "Female");
        genreECB.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        });

    }

    @FXML
    void AddE(ActionEvent event) {
        try {
            userService.add(new Entreprise(nomEnTF.getText(),emailEnTF.getText(),pwdEnTF.getText(),websiteEnTF.getText(),paysEnTF.getText(),localEnTF.getText()));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        closeStage();
    }

    @FXML
    void AddS(ActionEvent event) {
        try {
            userService.add(new Etudiant(nomTF.getText(), emailTF.getText(), pwdTF.getText(), Integer.parseInt(niveauTF.getText()), prenomTF.getText(), genreCB.getSelectionModel().getSelectedItem(), cinTF.getText(), classeTF.getText(), null, dateNaissanceTF.getValue()));

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        closeStage();
    }

    @FXML
    void AddT(ActionEvent event) {
        try {
            userService.add(new Enseignant(nomETF.getText(), emailETF.getText(), pdwETF.getText(), prenomETF.getText(), cinETF.getText(), genreECB.getSelectionModel().getSelectedItem(), dateNaissanceETF.getValue(), null));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) nomTF.getScene().getWindow();
        stage.close();
    }

}
