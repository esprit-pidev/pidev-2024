package Controllers.StageControllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CaptchaController {

    @FXML
    private WebView captchaWebView;

    public void initialize() {
        WebEngine engine = captchaWebView.getEngine();
        engine.load("https://www.google.com/recaptcha/api2/demo");

        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaController", this);
            }
        });
    }

    public void verifyCaptcha(String response) {
        try {
            String secretKey = "YOUR_SECRET_KEY"; // Remplacez par votre clé secrète reCAPTCHA
            String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + secretKey + "&response=" + response;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();
            boolean success = jsonObject.get("success").getAsBoolean();
            if (success) {
                // Captcha vérifié avec succès, ajoutez la candidature
                AjouterCandidatureController ajouterCandidatureController = new AjouterCandidatureController();
                ajouterCandidatureController.Ajouter(null); // Appel de la méthode Ajouter avec un événement null (à adapter selon vos besoins)
                System.out.println("Captcha vérifié avec succès !");
            } else {
                // Captcha échoué, afficher un message d'erreur
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez prouver que vous n'êtes pas un robot.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Une erreur s'est produite lors de la vérification du captcha.");
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}