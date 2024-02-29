package Controllers.StageControllers;

import tn.esprit.entities.stage.Offre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.stageServices.OffreService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import javafx.scene.control.Alert;
public class AjouterOffreController {
    private final OffreService OS= new OffreService();

    @FXML
    private TextField competences;


    @FXML
    private TextField description;

    @FXML
    private TextField nbr;

    @FXML
    private TextField titre;
    @FXML
    void add(ActionEvent event) throws IOException {
        if (titre.getText().isEmpty() || competences.getText().isEmpty() || description.getText().isEmpty() || nbr.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        int nombreOffres;
        try {
            nombreOffres = Integer.parseInt(nbr.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le nombre d'offres doit être un entier.");
            alert.showAndWait();
            return; // Sortir de la méthode si le nombre d'offres n'est pas un entier
        }

        // Remplacer 2 par l'entreprise_id correspondant à l'entreprise
        int entreprise_id = 2;

        if (OS.existeOffreAvecTitre(titre.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une offre avec ce titre existe déjà.");
            alert.showAndWait();
            return; // Sortir de la méthode si une offre avec le même titre existe déjà
        }

        OS.ajouter(new Offre(entreprise_id, titre.getText(),
                description.getText(),
                competences.getText(), nombreOffres,
                new Date()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'offre a été ajoutée avec succès !");
        alert.showAndWait();
    }

    @FXML
    void naviguezVersModifier(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ModifierOffre.fxml"));
            Parent root1 = loader1.load();

            // Passer des données à AfficherOffreController si nécessaire
            ModifierOffreController AO = loader1.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOffre.fxml"));
            Parent root = loader.load();

            // Passer des données à AfficherOffreController si nécessaire
            AfficherOffreController AO= loader.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }



    }

}
