package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ActivitesController {

    @FXML
    void NaviguerVersActivitéEsprit(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActiviteEsprit.fxml"));
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
    @FXML
    void NaviguerVersActiviteClub(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPublications.fxml"));
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
    @FXML
    void afficher(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPublications.fxml"));
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


}
