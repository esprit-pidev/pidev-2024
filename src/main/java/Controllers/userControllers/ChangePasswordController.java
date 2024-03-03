package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import tn.esprit.entities.User.User;
import tn.esprit.services.userServices.PasswordResetRequestService;
import tn.esprit.services.userServices.UserService;

import java.io.IOException;

public class ChangePasswordController {

    private final PasswordResetRequestService passwordResetRequestService = new PasswordResetRequestService();

    private final UserService userService = new UserService();

    private User user;

    public void initData(User user) {
        this.user = user;
    }

    @FXML
    private PasswordField confirmPwdTF;

    @FXML
    private PasswordField pwdTF;

    @FXML
    void annuler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        pwdTF.getScene().setRoot(root);
    }

    @FXML
    void save(ActionEvent event) throws IOException {
        if(!pwdTF.getText().equals(confirmPwdTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez confirmer avec la meme mot de passe");
            alert.showAndWait();
        } else if(pwdTF.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("La longeur minimale du mot de passe est 8");
            alert.showAndWait();
        }
        else {
            userService.changeMotDePasse(pwdTF.getText(),user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            pwdTF.getScene().setRoot(root);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("Mot de passe changer avec succés");
            alert.showAndWait();
        }
    }

}
