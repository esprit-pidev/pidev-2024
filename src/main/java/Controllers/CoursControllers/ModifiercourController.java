package Controllers.CoursControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.entities.Cours.Cours;
import tn.esprit.services.coursServices.CoursService;


import java.io.IOException;

public class ModifiercourController {

    @FXML
    private TextField coursurl;

    @FXML
    private TextField niveau;

    @FXML
    private TextField nomcour;

    @FXML
    private TextField nommodule;

    private Cours cours;
    private final CoursService cs = new CoursService();

    @FXML
    public void setCours(Cours cours) {
        this.cours = cours;
        // Update UI components with the Organisateur's data
        coursurl.setText(cours.getcoursURLpdf());
        niveau.setText(String.valueOf(cours.getNiveau()));
        nomcour.setText(cours.getNomcours());
        nommodule.setText(cours.getNommodule());

        // Set other fields as needed
    }

    @FXML
    void modifiercour(ActionEvent event) {
        // Récupération des valeurs depuis les champs de saisie
        String nomCours = nomcour.getText();
        String nomModule = nommodule.getText();
        String niveauStr = niveau.getText();

        // Validation des champs
        boolean vnom = validerNomCours(nomCours);
        boolean vnomModule = validerNomModule(nomModule);
        boolean vniveauStr = validerNiveau(niveauStr);

        if (vnom && vnomModule && vniveauStr) {
            // Conversion de niveau en entier après validation
            int niveauInt = Integer.parseInt(niveauStr);

            // Mise à jour du cours avec les valeurs validées
            cours.setCoursURLpdf(coursurl.getText());
            cours.setNiveau(niveauInt);
            cours.setNomcours(nomCours);
            cours.setNommodule(nomModule);

            cs.modifier(cours);
            naviguezVersAffichage(null);
        } else {
            // Afficher un message d'erreur en cas d'échec de la validation
            afficherMessageErreur("Erreur de validation. Vérifiez les champs.");
        }
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichercour.fxml"));
            nommodule.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validerNomCours(String nomCours) {
        // Vérification : nomCours doit être une chaîne non vide
        if (!nomCours.matches("[a-zA-Z]+") || nomCours.isEmpty()) {
            afficherMessageErreur("Nom du cours invalide.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerNomModule(String nomModule) {
        // Vérification : nomModule doit être une chaîne non vide et sa longueur ne doit pas dépasser 5
        if (nomModule.isEmpty() || nomModule.length() < 5) {
            afficherMessageErreur("Nom du module invalide.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerNiveau(String niveauStr) {
        try {
            // Tentative de conversion en entier
            int niveauInt = Integer.parseInt(niveauStr);

            // Vérification : niveauInt doit être un entier valide
            return true;
        } catch (NumberFormatException e) {
            // Échec de la conversion, ce n'est pas un entier valide
            afficherMessageErreur("Niveau invalide.");
            return false;
        }
    }
}




