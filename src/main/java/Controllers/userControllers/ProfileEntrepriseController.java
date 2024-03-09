package Controllers.userControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import tn.esprit.entities.User.Entreprise;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;

public class ProfileEntrepriseController {

    private final UserService userService = new UserService();

    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();


    @FXML
    private ImageView PhotoDeProfil;

    @FXML
    private ImageView ProfilPhoto;

    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;

    @FXML
    private HBox editProfil;

    @FXML
    private Label emailLB;

    @FXML
    private Label localLB;

    @FXML
    private HBox logout;

    @FXML
    private Label nomLB;

    @FXML
    private Label nomLBL;

    @FXML
    private HBox password;

    @FXML
    private Label paysLB;

    @FXML
    private Label websiteLB;


    public void initialize() {
        Entreprise entreprise =(Entreprise) userService.getById(userLoggedIn.getId());
        nomLBL.setText(entreprise.getNom());
        nomLBL.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        nomLB.setText(entreprise.getNom());
        nomLB.setStyle("-fx-text-fill: #9F1C00; -fx-font-weight: bold;");
        emailLB.setText(entreprise.getEmail());
        paysLB.setText(entreprise.getPays());
        localLB.setText(entreprise.getLocalisation());
        websiteLB.setText(entreprise.getWebsite());

        ProfilPhoto.setImage(new Image("\\images\\default-entr.jpg"));
        PhotoDeProfil.setImage(new Image("\\images\\default-entr.jpg"));

    }
    @FXML
    void goHome(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent root = loader.load();
        bp.getScene().setRoot(root);
    }
    @FXML
    void goToProfile(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileEntreprise.fxml"));
        Parent root = loader.load();
        bp.getScene().setRoot(root);
    }


    @FXML
    void editProfile(MouseEvent event) throws IOException {
        editProfil.setStyle("-fx-background-color : #781C10;");
        password.setStyle("-fx-background-color : #9F1C00;");
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/editProfileEntreprise.fxml"));
        bp.setCenter(root);

    }

    @FXML
    void editPassword(MouseEvent event) throws IOException {
        editProfil.setStyle("-fx-background-color : #9F1C00;");
        password.setStyle("-fx-background-color : #781C10;");
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/editPassword.fxml"));
        bp.setCenter(root);
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        UserSession.Logout();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        bp.getScene().setRoot(root);
    }
}
