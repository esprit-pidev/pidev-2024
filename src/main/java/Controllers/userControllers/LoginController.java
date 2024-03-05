package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.User.RememberMeToken;
import tn.esprit.entities.User.User;
import tn.esprit.services.userServices.*;

import java.awt.desktop.AboutEvent;
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
    private Pane content;

    @FXML
    private Button btnConx;

    @FXML
    private Button signUpbtn;

    @FXML
    private Label textSignUp;

    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField pwdTF;

    private int attempts = 0;

    @FXML
    void login(ActionEvent event) throws IOException {
        User user = userService.getByEmail(emailTF.getText());

        if (user != null) {
            if (user.getIsEnabled()) {
                if (BCrypt.checkpw(pwdTF.getText(), user.getPassword())) {
                    if (CaptchaService.isCaptchaCorrect()) {
                        attempts = 0;
                        userLoggedIn = new AuthResponseDTO(user.getId(), user.getRole());
                        UserSession.getSameInstance(userLoggedIn);
                        if (rememberMeCHB.isSelected()) {
                            String token = UUID.randomUUID().toString();
                            Preferences prefs = Preferences.userRoot().node("com/myapp");
                            prefs.put("rememberMeToken", token);
                            tokenService.add(new RememberMeToken(user, token, LocalDateTime.now().plusDays(10)));
                        }
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Activites.fxml"));
                        Parent root = loader.load();
                        emailTF.getScene().setRoot(root);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("CAPTCHA Error");
                        alert.setContentText("Veuillez repondre au CAPTCHA !");
                        alert.showAndWait();
                    }
                } else {
                    attempts++;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("password error !");
                    alert.showAndWait();
                    if (attempts > 1) {
                        CaptchaService.setCaptchaCorrect(false);

                        btnConx.setLayoutX(720);
                        btnConx.setLayoutY(586);

                        textSignUp.setLayoutX(724);
                        textSignUp.setLayoutY(639);

                        signUpbtn.setLayoutX(968);
                        signUpbtn.setLayoutY(631);

                        Pane captchaContainer = new Pane();
                        captchaContainer.setPrefHeight(70);
                        captchaContainer.setPrefWidth(306);
                        captchaContainer.setLayoutX(720); // X position
                        captchaContainer.setLayoutY(476);
                        content.getChildren().add(captchaContainer);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/captcha.fxml"));
                        Node captcha = loader.load();
                        captchaContainer.getChildren().add(captcha);
                    }
                }


            }
            else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Votre compte est bloquer pour le moment ! ");
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