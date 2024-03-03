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
import tn.esprit.entities.User.Enseignant;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.File;
import java.io.IOException;

public class ProfileEnseignantController {

    private final UserService userService = new UserService();

    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();


    @FXML
    private ImageView PhotoDeProfil;


    @FXML
    private Label dateNaissanceLB;

    @FXML
    private Label emailLB;

    @FXML
    private Label genreLB;

    @FXML
    private Label nomPrenomLB;

    @FXML
    private ImageView ProfilPhoto;

    @FXML
    AnchorPane ap;

    @FXML
    private HBox password;

    @FXML
    BorderPane bp;

    @FXML
    private HBox editProfil;

    @FXML
    private Label nomPrenomLBL;


    public void initialize() {
        String photoFileName="";
        Enseignant enseignant =(Enseignant) userService.getById(userLoggedIn.getId());
        photoFileName = "C:\\xampp\\htdocs\\img\\"+enseignant.getProfil_picture();
        nomPrenomLBL.setText(enseignant.getPrenom()+" "+enseignant.getNom());
        nomPrenomLBL.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        nomPrenomLB.setText(enseignant.getPrenom()+" "+enseignant.getNom());
        nomPrenomLB.setStyle("-fx-text-fill: #9F1C00; -fx-font-weight: bold;");
        loadImage(photoFileName);
        emailLB.setText(enseignant.getEmail());
        genreLB.setText(enseignant.getGenre());
        dateNaissanceLB.setText(String.valueOf(enseignant.getDate_naissance()));

    }

    private void loadImage(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            Image image = new Image(file.toURI().toString());
            ProfilPhoto.setImage(image);
            PhotoDeProfil.setImage(image);

        } else {
            System.out.println("Image file does not exist: " + filePath);
        }
    }


    @FXML
    void home(MouseEvent event) {
        bp.setCenter(ap);
    }

    @FXML
    void editProfile(MouseEvent event) throws IOException {
        editProfil.setStyle("-fx-background-color : #781C10;");
        password.setStyle("-fx-background-color : #9F1C00;");
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/editProfileEnseignant.fxml"));
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
