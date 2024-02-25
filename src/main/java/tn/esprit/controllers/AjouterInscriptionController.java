package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import tn.esprit.entities.extrascolaire.Inscription;
import tn.esprit.services.extrascolaireService.InscriptionService;

public class AjouterInscriptionController {

    private final InscriptionService is = new InscriptionService();

    @FXML
    private TextField emailTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField telTF;


    @FXML
    void passerPaiement(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EffectuerPaiement.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de l'interface de paiement
            EffectuerPaiementController effectuerPaiementController = loader.getController();

            // Passer les données du formulaire au contrôleur de paiement

            effectuerPaiementController.setEmailTF(emailTF);
            effectuerPaiementController.setNomTF(nomTF);
            effectuerPaiementController.setPrenomTF(prenomTF);
            effectuerPaiementController.setTelTF(telTF);

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
