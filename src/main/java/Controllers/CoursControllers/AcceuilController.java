package Controllers.CoursControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class AcceuilController {
    @FXML
    private Button buttonAdmin;

    @FXML
    void naviguezVersAdmin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichercour.fxml"));
            buttonAdmin.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersClient(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Eleve.fxml"));
            buttonAdmin.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
