package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import tn.esprit.entities.extrascolaire.Activite;
import tn.esprit.entities.extrascolaire.Inscription;
import tn.esprit.services.extrascolaireService.ActiviteService;
import tn.esprit.services.extrascolaireService.InscriptionService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserSession;

public class AjouterInscriptionController {
    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn();

    private final InscriptionService is = new InscriptionService();

    @FXML
    private TextField emailTF;

    public void setEmailTF(String emailTF) {
        this.emailTF.setText(emailTF);
    }

    public void setNomTF(String nomTF) {
        this.nomTF.setText(nomTF);
    }

    public void setPrenomTF(String prenomTF) {
        this.prenomTF.setText(prenomTF);
    }

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField telTF;

    private int activiteId;



    public void setActiviteId(int activiteID) {
        this.activiteId = activiteID;

    }


    @FXML
    void passerPaiement(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EffectuerPaiement.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de l'interface de paiement
            EffectuerPaiementController effectuerPaiementController = loader.getController();

            // Afficher l'ID dans la console
            System.out.println("ID de l'activité passé à EffectuerPaiement : " + activiteId);

            // Passer les données du formulaire au contrôleur de paiement
            effectuerPaiementController.setEmailTF(emailTF);
            effectuerPaiementController.setNomTF(nomTF);
            effectuerPaiementController.setPrenomTF(prenomTF);
            effectuerPaiementController.setTelTF(telTF);

            // Passer l'ID de l'activité correspondante au contrôleur de paiement
            effectuerPaiementController.setActiviteId(activiteId);

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
