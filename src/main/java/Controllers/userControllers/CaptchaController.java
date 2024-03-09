package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.services.userServices.CaptchaService;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class CaptchaController implements Initializable {

    @FXML
    private Label num1;
    @FXML
    private Label num2;
    @FXML
    private TextField answerField;

    @FXML
    private Label verified;

    @FXML
    Button verifyButton;

    private int expectedAnswer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateCaptcha();
    }

    @FXML
    void verify(ActionEvent event) {
        try {
            int userAnswer = Integer.parseInt(answerField.getText().trim());
            if (userAnswer == expectedAnswer) {
                verified.setText("CAPTCHA verifié avec succès !");
                 CaptchaService.setCaptchaCorrect(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Incorrecte CAPTCHA reponse");
                alert.setHeaderText(null);
                alert.setContentText("Incorrecte CAPTCHA reponse ! Veuillez réessayer.");
                alert.showAndWait();
                generateCaptcha();
                }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Valeur Numérique invalide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un entier.");
            alert.showAndWait();
            generateCaptcha();
        }

    }
    
    private void generateCaptcha() {
        Random random = new Random();
        int n1 = random.nextInt(10);
        int n2 = random.nextInt(10);
        expectedAnswer = n1 + n2;
        num1.setText(String.valueOf(n1));
        num2.setText(String.valueOf(n2));
        answerField.setText("");
    }
}
