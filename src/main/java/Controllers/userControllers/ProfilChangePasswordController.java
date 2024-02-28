package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.User.RoleName;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;

public class ProfilChangePasswordController {

    private final UserService userService = new UserService();

    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();

    @FXML
    private PasswordField pwdActuel;

    @FXML
    private PasswordField pwdConfirm;

    @FXML
    private PasswordField pwdNew;

    @FXML
    void cancel(ActionEvent event) throws IOException {
        if (userLoggedIn.getRole().equals(RoleName.STUDENT)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent root = loader.load();
            pwdNew.getScene().setRoot(root);
        }
        else if (userLoggedIn.getRole().equals(RoleName.TEACHER)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileEnseignant.fxml"));
            Parent root = loader.load();
            pwdNew.getScene().setRoot(root);
        }

    }

    @FXML
    void save(ActionEvent event) throws IOException {
        if (!BCrypt.checkpw(pwdActuel.getText(), userService.getById(userLoggedIn.getId()).getPassword())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Mot de passe actuel erroné !");
            alert.showAndWait();
        } else if (pwdNew.getText().length()<8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Le nouveau mot de passe doit etre de longeur minimale 8 !");
            alert.showAndWait();
        }
        else if (!pwdNew.getText().equals(pwdConfirm.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Confirmer Avec le meme mot de passe !");
            alert.showAndWait();
        } else {
            userService.changeMotDePasse(pwdNew.getText(),userService.getById(userLoggedIn.getId()));
            if (userLoggedIn.getRole().equals(RoleName.STUDENT)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
                Parent root = loader.load();
                pwdNew.getScene().setRoot(root);
            }
            else if (userLoggedIn.getRole().equals(RoleName.TEACHER)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileEnseignant.fxml"));
                Parent root = loader.load();
                pwdNew.getScene().setRoot(root);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("success");
            alert.setContentText("Mot de passe changé !");
            alert.showAndWait();
        }

    }

}
