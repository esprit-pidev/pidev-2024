package Controllers.activiteController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.extrascolaire.Inscription;
import tn.esprit.entities.extrascolaire.Paiement;
import tn.esprit.services.extrascolaireService.ActiviteService;
import tn.esprit.services.extrascolaireService.InscriptionService;
import tn.esprit.services.extrascolaireService.PaiementService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserSession;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class EffectuerPaiementController implements Initializable {
    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn();
    private final String ACCOUNT_SID = "AC3e08dfad3a4610724efd1ca1e38b47b1";
    private final String AUTH_TOKEN = "460e90ea4d3afea6351a10ae237de96c";
    private final String NUMERO_TWILIO = "+13852066665";
    @FXML
    private TextField nomD;

    @FXML
    private TextField numCarte;
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
    private TextField CVV;
    @FXML
    private Button buttonImprimerRecu;





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
    private int etudiantId;
    private double montantTotal;



    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }



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
    private ActiviteService activiteService = new ActiviteService();
    String nomActivite = activiteService.obtenirNomActivite(activiteId);







    @FXML
    void Payer(ActionEvent event) {

        try {
            // Récupérer l'ID de l'utilisateur connecté
            int etudiantId = userLoggedIn.getId();
            // Utiliser l'ID de l'activité déjà passé à cette interface
            int activiteId = this.activiteId;
            // Récupérer le montant total à payer en fonction de la sélection de l'utilisateur
            montantTotal = (PayerUnMois.isSelected()) ? 40 : 75;


            // Récupérer les valeurs des champs du formulaire
            String email = emailTF.getText();
            String nom = nomTF.getText();
            String prenom = prenomTF.getText();
            String tel = telTF.getText();
            String nomDetudiant = nomD.getText(); // Nouveau champ
            String numeroCarte = numCarte.getText(); // Nouveau champ
            // Récupérer les valeurs des nouveaux champs
            String moisSelectionne = mois.getValue();
            Integer anneeSelectionnee = annee.getValue();
            String cvvValue = CVV.getText();  // Le champ CVV
            // Créer un objet Inscription avec les valeurs
            Inscription nouvelleInscription = new Inscription();
            nouvelleInscription.setEmail(email);
            nouvelleInscription.setNom(nom);
            nouvelleInscription.setPrenom(prenom);
            nouvelleInscription.setNum_tel(tel);
            nouvelleInscription.setEtudiant_id(etudiantId);
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
                // Ajouter la validation pour le champ nomD
                if (nomDetudiant.isEmpty() || !nomDetudiant.matches("[a-zA-Z]+")) {
                    // Afficher une alerte indiquant à l'utilisateur de remplir le champ
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Champ non rempli");
                    alert.setHeaderText(null);
                    alert.setContentText("\"Le champ Nom du détenteur est obligatoire \"");
                    alert.showAndWait();
                    return;
                }


                // Ajouter la validation pour le champ mois
                if (moisSelectionne == null || moisSelectionne.isEmpty()) {
                    // Afficher une alerte indiquant à l'utilisateur de remplir le champ
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Champ non rempli");
                    alert.setHeaderText(null);
                    alert.setContentText("Mois non spécifié,Veuillez sélectionner un mois.");
                    alert.showAndWait();
                    return;
                }

                // Ajouter la validation pour le champ annee
                if (anneeSelectionnee == null) {
                    // Afficher une alerte indiquant à l'utilisateur de remplir le champ
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Champ non rempli");
                    alert.setHeaderText(null);
                    alert.setContentText("Année non spécifiée, Veuillez sélectionner une année.");
                    alert.showAndWait();
                    return;
                }
                // Ajouter la validation pour le champ numCarte
                if (numeroCarte.isEmpty() || !numeroCarte.matches("\\d{16}")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Champ non rempli");
                    alert.setHeaderText(null);
                    alert.setContentText("Le champ Numéro de la carte est obligatoire.");
                    alert.showAndWait();
                    return;
                }

                // Ajouter la validation pour le champ CVV
                if (cvvValue.isEmpty() || !cvvValue.matches("\\d{4}")) {
                    // Afficher une alerte indiquant à l'utilisateur de remplir le champ
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Champ non rempli");
                    alert.setHeaderText(null);
                    alert.setContentText("Champ CVV non rempli.  ");
                    alert.showAndWait();
                    return;
                }



                if (paiementEffectue != null) {
                    System.out.println("Paiement effectué avec succès. ID: " + paiementEffectue.getId());
                    // Activer le bouton "Imprimer Reçu" car le paiement a été effectué
                    buttonImprimerRecu.setDisable(false);
                    // Mettez à jour l'étiquette ou effectuez d'autres actions si nécessaire
                    updateMontantTotalLabel("Montant total à payer : ");
                    // Envoyer un SMS
                    sendPaymentConfirmationSMS(inscriptionAjoutee.getDateInscription(), montantTotal);

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
    private void sendPaymentConfirmationSMS(String dateInscription, double montantTotal) {
        // Formater le message SMS
        String messageSMS = "Félicitations, le paiement de votre inscription a été effectué avec succès le "
                + dateInscription + ". Le montant payé est de " + montantTotal + " Dt. Bienvenue chez EspritEduSmart.";

        // Initialiser Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Remplacez "userPhoneNumber" par le véritable numéro de téléphone de l'utilisateur obtenu à partir du formulaire
        String userPhoneNumber = telTF.getText();

        // Supprimez les espaces et les caractères non numériques dans le numéro de téléphone
        userPhoneNumber = userPhoneNumber.replaceAll("[^0-9]", "");

        // Envoyer le SMS
        Message message = Message.creator(
                        new PhoneNumber("+" + userPhoneNumber),  // Ajoutez le préfixe international
                        new PhoneNumber(NUMERO_TWILIO),  // Numéro de téléphone Twilio
                        messageSMS)
                .create();

        System.out.println("SMS envoyé : " + message.getSid());
    }
    @FXML
    void imprimerRecu(ActionEvent event) {
        // Obtenir le nom de l'activité à partir de l'ID en utilisant ActiviteService
        String nomActivite = activiteService.obtenirNomActivite(activiteId);

        try {
            // Vérifier si le paiement a été effectué
            if (buttonImprimerRecu.isDisable()) {
                // Afficher un message d'erreur à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez effectuer le paiement avant de pouvoir imprimer le reçu.");
                alert.showAndWait();
            } else {
                // Créer un sélecteur de fichiers
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Enregistrer le reçu de paiement");
                fileChooser.setInitialFileName("recu_paiement.pdf");

                // Afficher la boîte de dialogue de sauvegarde de fichier
                File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

                if (selectedFile != null) {
                    String cheminFichier = selectedFile.getAbsolutePath();
                    RecuPaiement.genererRecuPDF(nomTF.getText(), prenomTF.getText(), String.valueOf(montantTotal), nomActivite, cheminFichier);

                    // Afficher un message de confirmation à l'utilisateur
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Reçu de Paiement");
                    alert.setHeaderText(null);
                    alert.setContentText("Le reçu de paiement a été généré avec succès et enregistré sous : " + cheminFichier);
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        // Désactiver le bouton "Imprimer Reçu" au début
        buttonImprimerRecu.setDisable(true);
        // Initialiser les ChoiceBox avec les options nécessaires

        initializeMoisChoiceBox();
        initializeAnneeChoiceBox();

        // Initialiser le ToggleGroup pour les boutons radio
        toggleGroup = new ToggleGroup();
        PayerUnMois.setToggleGroup(toggleGroup);
        PayerDeuxMois.setToggleGroup(toggleGroup);

        // Lier l'événement du bouton "Imprimer Reçu" à la méthode imprimerRecu
        buttonImprimerRecu.setOnAction(this::imprimerRecu);

    }



    private void initializeMoisChoiceBox() {
        // Ajouter des options pour les mois
        mois.getItems().addAll("01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12");
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