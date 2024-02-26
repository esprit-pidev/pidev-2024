package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

public class ActiviteEspritController {
    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn();
    private final UserService us = new UserService();


    private int activiteId;

    @FXML
    private Button buttonActivite1;

    @FXML
    private Button buttonActivite2;

    @FXML
    private Button buttonActivite3;

    public void setActiviteId(int activiteID) {
        this.activiteId = activiteID;

    }
    private int obtenirIdActiviteDepuisButton(Button button) {
        if (button.equals(buttonActivite1)) {
            return 1; // ID de l'activité 1
        } else if (button.equals(buttonActivite2)) {
            return 2; // ID de l'activité 2
        } else if (button.equals(buttonActivite3)) {
            return 3; // ID de l'activité 3
        } else {
            return -1; // Retourne -1 si l'ID de l'activité n'est pas trouvé
        }
    }

    @FXML
    void NaviguerVersInscription(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface d'ajout d'inscription
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterInscription.fxml"));
            Parent root = loader.load();
            AjouterInscriptionController ajouterInscriptionController = loader.getController();
            Etudiant etudiant = (Etudiant) us.getById(userLoggedIn.getId());
            ajouterInscriptionController.setNomTF(etudiant.getNom());
            ajouterInscriptionController.setPrenomTF(etudiant.getPrenom());
            ajouterInscriptionController.setPrenomTF(etudiant.getEmail());

            // Obtenir le contrôleur de l'interface d'ajout d'inscription
            AjouterInscriptionController AjouterInscriptionController = loader.getController(); // Correction du nom de la classe

            // Récupérer l'ID de l'activité à partir du bouton cliqué
            Button clickedButton = (Button) event.getSource();
            int activiteId = obtenirIdActiviteDepuisButton(clickedButton);
            // Afficher l'ID dans la console
            System.out.println("ID de l'activité passé à AjouterInscription : " + activiteId);

            // Passer l'ID de l'activité au contrôleur de l'interface d'ajout d'inscription
            AjouterInscriptionController.setActiviteId(activiteId); // Correction du nom de la classe

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
    void Retour(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Activites.fxml"));
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
