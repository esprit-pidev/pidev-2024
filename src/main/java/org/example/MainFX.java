package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
        Parent root = loader.load();
        Scene scene =new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("bingo");
        primaryStage.show();

        FXMLLoader Loader3 = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root3 = Loader3.load();
        Scene scene3 =new Scene(root3);
        primaryStage.setScene(scene3);
        primaryStage.setTitle("login");
        primaryStage.show();

      /*FXMLLoader Loader2 = new FXMLLoader(getClass().getResource("/Statistique.fxml"));
        Parent root2 = Loader2.load();
        Scene scene2 =new Scene(root2);
        primaryStage.setScene(scene2);
        primaryStage.setTitle("Statistique");
        primaryStage.show();

        FXMLLoader Loader4 = new FXMLLoader(getClass().getResource("/LesOffresDEStage.fxml"));
        Parent root4 = Loader4.load();
        Scene scene4 =new Scene(root4);
        primaryStage.setScene(scene4);
        primaryStage.setTitle("Les Offres De Stage");
        primaryStage.show();

 */
    }
}
