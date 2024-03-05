package Controllers.activiteController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
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
    public void initialize() {
        // Utiliser un TextFormatter avec UnaryOperator pour permettre uniquement les chiffres
        telTF.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[\\d+]*")) {  // [\\d+]* signifie "zéro ou plusieurs chiffres ou le caractère +"
                return change;
            } else {
                return null;  // Rejeter le changement si le texte ne contient pas uniquement des chiffres ou le caractère "+"
            }
        }));

    }


    @FXML
    void passerPaiement(ActionEvent event) {
        // Vérifier si le champ telTF est vide
        if (telTF.getText().isEmpty()) {
            // Afficher une alerte indiquant à l'utilisateur de remplir le champ
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ non rempli");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le champ de numéro de téléphone avant de passer au paiement.");
            alert.showAndWait();
            return;  // Arrêter la méthode si le champ n'est pas rempli
        }
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EffectuerPaiement.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de l'interface de paiement
            EffectuerPaiementController effectuerPaiementController = loader.getController();

            // Afficher l'ID dans la console
            System.out.println("ID de l'activité passé à EffectuerPaiement : " + activiteId);

            // Récupérer l'ID de l'utilisateur connecté
            int etudiantId = userLoggedIn.getId();

            // Passer les données du formulaire au contrôleur de paiement
            effectuerPaiementController.setEmailTF(emailTF);
            effectuerPaiementController.setNomTF(nomTF);
            effectuerPaiementController.setPrenomTF(prenomTF);
            effectuerPaiementController.setTelTF(telTF);

            // Passer l'ID de l'activité et de l'étudiant correspondants au contrôleur de paiement
            effectuerPaiementController.setActiviteId(activiteId);
            effectuerPaiementController.setEtudiantId(etudiantId);

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
