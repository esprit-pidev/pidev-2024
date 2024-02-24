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


        FXMLLoader Loader3 = new FXMLLoader(getClass().getResource("/AjouterOffre.fxml"));
        Parent root3 = Loader3.load();
        Scene scene3 =new Scene(root3);
        primaryStage.setScene(scene3);
        primaryStage.setTitle("Ajouter offre");
        primaryStage.show();



    }
}
