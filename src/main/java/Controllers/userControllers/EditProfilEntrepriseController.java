package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.User.Entreprise;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;

public class EditProfilEntrepriseController {

    private final UserService userService = new UserService();

    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn();

    @FXML
    private AnchorPane ap;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField localTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField paysTF;

    @FXML
    private ImageView profilPhoto;

    @FXML
    private TextField websiteTF;

    public void initialize() throws IOException {

        Entreprise entreprise = (Entreprise) userService.getById(userLoggedIn.getId());
        setNomTF(entreprise.getNom());
        setEmailTF(entreprise.getEmail());
        setLocalTF(entreprise.getLocalisation());
        setPaysTF(entreprise.getPays());
        setWebsiteTF(entreprise.getWebsite());
        profilPhoto.setImage(new Image("\\images\\default-entr.jpg"));
    }




    public void setNomTF(String nomTF) {
        this.nomTF.setText(nomTF);
    }

    public void setEmailTF(String emailTF) {
        this.emailTF.setText(emailTF);
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
    void saveChanges(ActionEvent event) throws IOException {
        Entreprise entreprise = (Entreprise) userService.getById(userLoggedIn.getId());
        entreprise.setNom(nomTF.getText());
        entreprise.setEmail(emailTF.getText());
        entreprise.setLocalisation(localTF.getText());
        entreprise.setPays(paysTF.getText());
        entreprise.setWebsite(websiteTF.getText());
        userService.adminUpdateEntreprise(entreprise);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileEntreprise.fxml"));
        Parent root = loader.load();
        nomTF.getScene().setRoot(root);
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileEntreprise.fxml"));
        Parent root = loader.load();
        nomTF.getScene().setRoot(root);
    }
}
