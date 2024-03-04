package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.entities.User.RememberMeToken;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.RememberMeTokenService;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.prefs.Preferences;

public class MainFX extends Application {

    AuthResponseDTO userLoggedIn;

    private final RememberMeTokenService tokenService = new RememberMeTokenService();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 768);
        primaryStage.setScene(scene);
        primaryStage.setTitle("bingo");
        primaryStage.show();






    }
}
