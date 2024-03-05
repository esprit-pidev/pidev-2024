package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.entities.User.RememberMeToken;
import tn.esprit.entities.User.RoleName;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.RememberMeTokenService;
import tn.esprit.services.userServices.UserService;
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

        Preferences prefs = Preferences.userRoot().node("com/myapp");
        String token = prefs.get("rememberMeToken", null);
        if (token != null) {
            RememberMeToken rememberMeToken = tokenService.getByToken(token);
            if (rememberMeToken.getExpiresAt().isAfter(LocalDateTime.now())) {
                userLoggedIn = new AuthResponseDTO(rememberMeToken.getUser().getId(), rememberMeToken.getUser().getRole());
                UserSession.getSameInstance(userLoggedIn);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affichercour.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root,1280,768);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Profile");
                primaryStage.show();
            } else {
                tokenService.deleteById(rememberMeToken.getId());
                prefs.remove("rememberMeToken");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Login");
                primaryStage.show();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Error");
                alert.setContentText("token expired !");
                alert.showAndWait();
            }

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,1280,768);
            primaryStage.setScene(scene);
            primaryStage.setTitle("home");
            primaryStage.show();
        }
    }
}