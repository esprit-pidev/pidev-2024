package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.User.RememberMeToken;
import tn.esprit.entities.User.User;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.RememberMeTokenService;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.prefs.Preferences;

public class LoginController {

    private final UserService userService = new UserService();

    private final RememberMeTokenService tokenService = new RememberMeTokenService();

    AuthResponseDTO userLoggedIn;

    @FXML
    private CheckBox rememberMeCHB;

    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField pwdTF;

    @FXML
    void login(ActionEvent event) throws IOException {
        User user = userService.getByEmail(emailTF.getText());

        if (user != null) {
            if (BCrypt.checkpw(pwdTF.getText(), user.getPassword())) {
                userLoggedIn = new AuthResponseDTO(user.getId(), user.getRole());
                UserSession.getSameInstance(userLoggedIn);


                if (rememberMeCHB.isSelected()) {
                    String token = UUID.randomUUID().toString();
                    Preferences prefs = Preferences.userRoot().node("com/myapp");
                    prefs.put("rememberMeToken", token);
                    tokenService.add(new RememberMeToken(user,token,LocalDateTime.now().plusDays(10)));
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
                Parent root = loader.load();
                emailTF.getScene().setRoot(root);
            } else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("password error !");
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("email error !");
            alert.showAndWait();
        }


    }

    @FXML
    void forgetPassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPassword.fxml"));
        Parent root = loader.load();
        emailTF.getScene().setRoot(root);
    }

    @FXML
    void SignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
        Parent root = loader.load();
        emailTF.getScene().setRoot(root);
    }

}