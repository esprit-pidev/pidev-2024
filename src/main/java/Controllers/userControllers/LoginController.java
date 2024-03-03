package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.User.User;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;

public class LoginController {

    private final UserService userService = new UserService();

    AuthResponseDTO userLoggedIn;

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayEvent.fxml"));
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

}
