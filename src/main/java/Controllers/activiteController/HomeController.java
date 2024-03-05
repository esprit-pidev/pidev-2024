package Controllers.activiteController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.entities.User.User;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Button connecter;

    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();
    private final User user =userLoggedIn;


    @FXML
    void NaviguerVerslogin(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            // Accéder au stage actuel à partir de n'importe quel nœud de la scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Remplacer le contenu de la scène actuelle avec le nouveau contenu
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions comme le chargement du FXML échoue
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user != null){
            connecter.setVisible(false);
        }
    }
}
