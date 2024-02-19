package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.User.Entreprise;
import tn.esprit.services.userServices.UserService;


public class EntrepriseEditFormController {

    private final UserService userService = new UserService();

    private Entreprise entreprise;

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    @FXML
    private TextField emailTF;

    @FXML
    private TextField localTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField paysTF;

    @FXML
    private TextField websiteTF;


    public void setEmailTF(String emailTF) {
        this.emailTF.setText(emailTF);
    }

    public void setNomTF(String nomTF) {
        this.nomTF.setText(nomTF);
    }

    public void setLocalTF(String localTF) {
        this.localTF.setText(localTF);
    }

    public void setPaysTF(String paysTF) {
        this.paysTF.setText(paysTF);
    }

    public void setWebsiteTF(String websiteTF) {
        this.websiteTF.setText(websiteTF);
    }

    @FXML
    void SaveEntreprise(ActionEvent event) {
        entreprise.setNom(nomTF.getText());
        entreprise.setPays(paysTF.getText());
        entreprise.setLocalisation(localTF.getText());
        entreprise.setWebsite(websiteTF.getText());
        entreprise.setEmail(emailTF.getText());
        userService.adminUpdateEntreprise(entreprise);
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) nomTF.getScene().getWindow();
        stage.close();
    }

}
