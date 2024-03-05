package Controllers.StageControllers;

import tn.esprit.entities.User.Entreprise;
import tn.esprit.entities.stage.Offre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.stageServices.OffreService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;
import java.util.Date;

public class AjouterOffreController {
    private final OffreService OS = new OffreService();
    private final UserService us = new UserService();

    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn();

    Entreprise entreprise = (Entreprise) us.getById(userLoggedIn.getId());

    @FXML
    private TextField competences;

    @FXML
    private TextField description;

    @FXML
    private TextField nbr;

    @FXML
    private TextField titre;

    @FXML
    void add(ActionEvent event) {
        if (titre.getText().isEmpty() || competences.getText().isEmpty() || description.getText().isEmpty() || nbr.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez remplir tous les champs.");
            return;
        }

        int nombreOffres;
        try {
            nombreOffres = Integer.parseInt(nbr.getText());
            if (nombreOffres <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Le nombre d'offres doit être un entier positif.");
            return;
        }

        if (OS.existeOffreAvecTitre(titre.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Une offre avec ce titre existe déjà.");
            return;
        }

        OS.ajouter(new Offre(entreprise, titre.getText(), description.getText(), competences.getText(), nombreOffres, new Date()));

        showAlert(Alert.AlertType.INFORMATION, "Succès", null, "L'offre a été ajoutée avec succès !");
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOffre.fxml"));
            Parent root = loader.load();

            // Passer des données à AfficherOffreController si nécessaire
            AfficherOffreController AO = loader.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    @FXML
    void afficherStatistique(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Statistique.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
