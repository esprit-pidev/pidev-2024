package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.User.User;
import tn.esprit.entities.extrascolaire.Inscription;
import tn.esprit.entities.extrascolaire.Paiement;
import tn.esprit.services.extrascolaireService.InscriptionService;
import tn.esprit.services.extrascolaireService.PaiementService;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class EffectuerPaiementController implements Initializable {
    @FXML
    private Button buttonActivite1;

    @FXML
    private Button buttonActivite2;

    @FXML
    private Button buttonActivite3;
    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<Integer> annee;

    @FXML
    private ChoiceBox<Integer> jour;

    @FXML
    private ChoiceBox<String> mois;

    @FXML
    private SubScene sectionright;

    @FXML
    private RadioButton PayerUnMois;

    @FXML
    private RadioButton PayerDeuxMois;

    @FXML
    private Label affichage;

    @FXML
    private Label montantTotalLabel;

    private ToggleGroup toggleGroup;

    // Utilisez TextField au lieu de java.awt.Label pour les champs de texte
    @FXML
    private TextField emailTF;

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
    public void setEmailTF(TextField emailTF) {
        this.emailTF = emailTF;
    }

    public void setNomTF(TextField nomTF) {
        this.nomTF = nomTF;
    }

    public void setPrenomTF(TextField prenomTF) {
        this.prenomTF = prenomTF;
    }

    public void setTelTF(TextField telTF) {
        this.telTF = telTF;
    }


    @FXML
    void Payer(ActionEvent event) {
        try {
            // Récupérer l'ID de l'utilisateur existant (remplacez cela par l'ID réel)
            int existingUserId = 1;
            // Utiliser l'ID de l'activité déjà passé à cette interface
            int activiteId = this.activiteId;


            // Récupérer les valeurs des champs du formulaire
            String email = emailTF.getText();
            String nom = nomTF.getText();
            String prenom = prenomTF.getText();
            String tel = telTF.getText();

            // Créer un objet Inscription avec les valeurs
            Inscription nouvelleInscription = new Inscription();
            nouvelleInscription.setEmail(email);
            nouvelleInscription.setNom(nom);
            nouvelleInscription.setPrenom(prenom);
            nouvelleInscription.setNum_tel(tel);
            nouvelleInscription.setEtudiant_id(existingUserId);
            nouvelleInscription.setActivite_id(activiteId);

            // Appeler la méthode ajouterInscription du service
            InscriptionService inscriptionService = new InscriptionService();
            Inscription inscriptionAjoutee = inscriptionService.ajouterInscription(nouvelleInscription);

            if (inscriptionAjoutee != null) {
                // Inscription réussie, vous pouvez effectuer d'autres actions si nécessaire

                // Récupérer le montant total à payer en fonction de la sélection de l'utilisateur
                double montantTotal = (PayerUnMois.isSelected()) ? 40 : 75;

                // Appeler la méthode effectuerPaiement du service pour ajouter le paiement
                PaiementService paiementService = new PaiementService();
                Paiement paiementEffectue = paiementService.effectuerPaiement(inscriptionAjoutee.getId(), montantTotal);

                if (paiementEffectue != null) {
                    System.out.println("Paiement effectué avec succès. ID: " + paiementEffectue.getId());
                    // Mettez à jour l'étiquette ou effectuez d'autres actions si nécessaire
                    updateMontantTotalLabel("Montant total à payer : ");
                } else {
                    System.out.println("Erreur lors de l'ajout du paiement.");
                }
            } else {
                // Gérer le cas où l'inscription n'a pas pu être ajoutée
                System.out.println("Erreur lors de l'ajout de l'inscription.");
            }
        } catch (Exception e) {
            // Capturer l'exception d'invocation
            if (e instanceof InvocationTargetException && e.getCause() != null) {
                e.getCause().printStackTrace();
            } else {
                e.printStackTrace();
            }
        }
    }





    @FXML
    void PayerUnMoisSelected(ActionEvent event) {
        updateMontantTotalLabel("Montant total à payer : 40D");
    }

    @FXML
    void PayerDeuxMoisSelected(ActionEvent event) {
        updateMontantTotalLabel("Montant total à payer : 75D");
    }


    @FXML
    void Retour(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterInscription.fxml"));
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

    public void initialize(URL location, ResourceBundle resources) {
        // Initialiser les ChoiceBox avec les options nécessaires
        initializeJourChoiceBox();
        initializeMoisChoiceBox();
        initializeAnneeChoiceBox();

        // Initialiser le ToggleGroup pour les boutons radio
        toggleGroup = new ToggleGroup();
        PayerUnMois.setToggleGroup(toggleGroup);
        PayerDeuxMois.setToggleGroup(toggleGroup);

    }

    private void initializeJourChoiceBox() {
        // Ajouter des options pour les jours (par exemple, de 1 à 31)
        for (int i = 1; i <= 31; i++) {
            jour.getItems().add(i);
        }
    }

    private void initializeMoisChoiceBox() {
        // Ajouter des options pour les mois
        mois.getItems().addAll("Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre");
    }

    private void initializeAnneeChoiceBox() {
        // Ajouter des options pour les années (par exemple, de l'année actuelle à 10 ans plus tard)
        int currentYear = java.time.Year.now().getValue();
        for (int i = currentYear; i <= currentYear + 10; i++) {
            annee.getItems().add(i);
        }
    }

    private void updateMontantTotalLabel(String text) {
        montantTotalLabel.setText(text);
    }


}
