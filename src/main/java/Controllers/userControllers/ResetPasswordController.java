package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.entities.User.PasswordResetRequest;
import tn.esprit.services.userServices.PasswordResetRequestService;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MailSender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class ResetPasswordController {

    private final UserService userService = new UserService();

    private final PasswordResetRequestService passwordResetRequestService = new PasswordResetRequestService();


    @FXML
    private TextField codeTF;

    @FXML
    private TextField emailTF;

    @FXML
    void envoiCode(ActionEvent event) {
        if (userService.getByEmail(emailTF.getText())==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email error");
            alert.setHeaderText(null);
            alert.setContentText("Aucun utilisateur avec cette email");
            alert.showAndWait();
        }
        else {
            if (passwordResetRequestService.getByUser(userService.getByEmail(emailTF.getText()))!=null)
                passwordResetRequestService.delete(userService.getByEmail(emailTF.getText()));
            int verifCode = ThreadLocalRandom.current().nextInt(10000, 100000);
            passwordResetRequestService.Add(new PasswordResetRequest(verifCode, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), true, userService.getByEmail(emailTF.getText())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email envoyé");
            alert.setHeaderText(null);
            alert.setContentText("Code envoyé !! Verifiez votre email");
            alert.showAndWait();
            MailSender.sendEmail(emailTF.getText(), "Vérification Email", "Votre code de vérification est : " + verifCode);
        }
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        if (userService.getByEmail(emailTF.getText())!=null)
            passwordResetRequestService.delete(userService.getByEmail(emailTF.getText()));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        emailTF.getScene().setRoot(root);
    }

    @FXML
    void verif(ActionEvent event) throws IOException {
        if (emailTF.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email error");
            alert.setHeaderText(null);
            alert.setContentText("Entrer votre email pour recevoir le code");
            alert.showAndWait();
        }
        else if (passwordResetRequestService.getByUser(userService.getByEmail(emailTF.getText())).getResetCode() != Integer.parseInt(codeTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Code error");
            alert.setHeaderText(null);
            alert.setContentText("Code erroné");
            alert.showAndWait();
        }
        else if (LocalDateTime.now().isAfter(passwordResetRequestService.getByUser(userService.getByEmail(emailTF.getText())).getExpiresAt())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Code expiré");
            alert.setHeaderText(null);
            alert.setContentText("Code expiré");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChangePassword.fxml"));
            Parent root = loader.load();
            ChangePasswordController changePasswordController = loader.getController();
            changePasswordController.initData(userService.getByEmail(emailTF.getText()));
            passwordResetRequestService.delete(userService.getByEmail(emailTF.getText()));
            codeTF.getScene().setRoot(root);
        }
    }

}
